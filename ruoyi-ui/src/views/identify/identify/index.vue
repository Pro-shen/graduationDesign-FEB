<template>
  <div class="container">
    <div style="height: 100%;width: 100%;display: flex;flex-direction: row;">
      <div
        style="width: 50%;height: 100%;display: flex;align-items: center;justify-content: center;flex-direction: column;">
        <video style="width: 100%;" ref="videoElement" autoplay></video>
        <div style="height: 15%;" />
      </div>
      <div
        style="width: 50%;height: 100%;display: flex;align-items: center;justify-content: center;flex-direction: column;">
        <div style="width: 100%;height: 50%;display: flex;align-items: center;justify-content: center;">
          <canvas ref="canvasElement" style="display: none;"></canvas>
          <img :src="verificationImg" />
        </div>
        <div
          style="height: 50%;width: 100%;display: flex;align-items: center;justify-content: center;flex-direction: column;">
          <div style="width: 100%;display: flex;align-items: center;justify-content: center;">
            <el-radio-group v-model="deviceslists" size="medium" @change="changeSelect">
              <el-radio v-for="(item, index) in deviceslist" :key="index" :label=item.index>{{ item.label }}</el-radio>
            </el-radio-group>
          </div>
          <div style="display: flex;flex-direction: row;align-items: center;justify-content: center;height: 30%;">
            <el-button @click="toggleCamera">{{ cameraActive ? '关闭摄像头' : '打开摄像头' }}</el-button>
            <el-button @click="uploadloadImage">开始识别</el-button>
          </div>
          <div style="height: 30%;width: 100%;display: flex;flex-direction: column;align-items: center;justify-content: center;">
            <div v-for="(item, index) in objectList" :key="index" :label="item.index" style="font-size: 20;">
              {{dict.type.t_plate_color[item.dictValue].label}}:{{item.number}}
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { list, upload } from "@/api/identify/identify"
export default {
  dicts: ['t_plate_color'],
  data() {
    return {
      queryParams: {
        tenantId: 1,
        pageNum: 1,
        pageSize: 10,
        createTime: undefined
      },
      cameraindex: undefined,
      deviceslists: null,
      deviceslist: [],
      dateRange: [],
      base64Info: {},
      objectList:[],
      cameraActive: false,
      snapshotUrl: '',
      stream: null,
      videoElement: null,
      canvasElement: null,
      verificationImg: '',
      rotate: {
        x: 0,
        y: 0,
      },
    }
  },
  mounted() {
    this.videoElement = this.$refs.videoElement;
    this.canvasElement = this.$refs.canvasElement;
  },
  created() {
    // this.getList();
    this.selectCamera()
  },
  methods: {
    selectCamera() {
      var that = this
      this.deviceslist = []
      var n = 0;
      navigator.mediaDevices.enumerateDevices().then(function (devices) {
        for (var i = 0; i < devices.length; i++) {
          if (devices[i].kind === 'videoinput') {
            that.deviceslist.push({
              label: devices[i].label,
              deviceId: devices[i].deviceId,
              index: n
            })
            n++
          }
        }
      })
    },
    changeSelect(value) {
      this.cameraindex = value
    },
    getList() {
      list(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.tPerformanceList = response.rows
        this.loading = false
        this.total = response.total
      })
    },
    toggleCamera() {
      var that = this
      if (this.cameraindex == undefined) {
        this.$modal.msgError("请选择摄像头")
      } else {
        if (!this.cameraActive) {
          navigator.mediaDevices.getUserMedia({ video: { deviceId: this.deviceslist[this.cameraindex].deviceId } })
            .then(function (stream) {
              that.stream = stream;
              that.videoElement.srcObject = stream;
              that.cameraActive = true;
            })
            .catch(function (err) {
              console.log('无法打开摄像头', err);
            })
        } else {
          this.videoElement.srcObject = null;
          this.stream.getTracks().forEach((track) => {
            track.stop();
          });
          this.cameraActive = false;
        }
      }
    },
    uploadloadImage() {
      if (this.cameraindex != undefined) {
        this.canvasElement.width = this.videoElement.videoWidth / 3 * 2;
        this.canvasElement.height = this.videoElement.videoHeight / 3 * 2;
        const context = this.canvasElement.getContext('2d')
        const width = this.videoElement.videoWidth / 3 * 2
        const height = this.videoElement.videoHeight / 3 * 2
        context.drawImage(this.videoElement, 0, 0, width, height);
        this.snapshotUrl = this.canvasElement.toDataURL();
        if (this.snapshotUrl) {
          this.base64Info = {}
          this.base64Info.base64 = this.snapshotUrl
          upload(this.base64Info).then(res => {
            this.objectList = res.data.body.objectList
            console.log(this.objectList)
            var img_str = "data:image/png;base64," + res.data.body.base64
            var imgFile = this.base64ImgtoFile(img_str);
            let blob = new Blob([imgFile], { type: 'image/png' })
            let imageUrl = (window.URL || window.wekitURL).createObjectURL(blob)
            this.verificationImg = imageUrl
          })
        }
      } else {
        this.$modal.msgError("请打开摄像头")
      }
    },
    base64ImgtoFile(dataurl, filename = 'file') {
      let arr = dataurl.split(',')
      let mime = arr[0].match(/:(.*?);/)[1]
      let suffix = mime.split('/')[1]
      let bstr = atob(arr[1])
      let n = bstr.length
      let u8arr = new Uint8Array(n)
      while (n--) {
        u8arr[n] = bstr.charCodeAt(n)
      }
      return new File([u8arr], `${filename}.${suffix}`, {
        type: mime
      })
    },
  }
}
</script>

<style>
.container {
  width: 100%;
  height: 80vh;
  display: flex;
  flex-direction: row;
}
</style>



