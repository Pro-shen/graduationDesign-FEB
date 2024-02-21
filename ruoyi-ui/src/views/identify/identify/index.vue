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
          <canvas ref="canvasElement"></canvas>
        </div>
        <div style="height: 50%;width: 100%;display: flex;align-items: center;justify-content: center;">
          <el-button @click="toggleCamera">{{ cameraActive ? '关闭摄像头' : '打开摄像头' }}</el-button>
          <el-button @click="takeSnapshot">拍摄</el-button>
          <el-button @click="downloadImage">下载图片</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { list, upload } from "@/api/identify/identify"
export default {
  data() {
    return {
      queryParams: {
        tenantId: 1,
        pageNum: 1,
        pageSize: 10,
        createTime: undefined
      },
      dateRange: [],
      base64Info: {},
      cameraActive: false,
      snapshotUrl: '',
      stream: null,
      videoElement: null,
      canvasElement: null,
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
  },
  methods: {
    getList() {
      list(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.tPerformanceList = response.rows
        this.loading = false
        this.total = response.total
      })
    },
    toggleCamera() {
      if (!this.cameraActive) {
        navigator.mediaDevices.getUserMedia({ video: true })
          .then((stream) => {
            this.stream = stream;
            this.videoElement.srcObject = stream;
            this.cameraActive = true;
          })
          .catch((error) => {
            console.error('无法打开摄像头:', error);
          });
      } else {
        this.videoElement.srcObject = null;
        this.stream.getTracks().forEach((track) => {
          track.stop();
        });
        this.cameraActive = false;
      }
    },
    takeSnapshot() {
      this.canvasElement.width = this.videoElement.videoWidth / 2;
      this.canvasElement.height = this.videoElement.videoHeight / 2;
      const context = this.canvasElement.getContext('2d')
      const width = this.videoElement.videoWidth
      const height = this.videoElement.videoHeight
      context.drawImage(this.videoElement, 0, 0, width / 2, height / 2);
      this.snapshotUrl = this.canvasElement.toDataURL();
    },
    downloadImage() {
      if (this.snapshotUrl) {
        this.base64Info = {}
        this.base64Info.base64 = this.snapshotUrl
        upload(this.base64Info).then(res=>{
          console.log(res)
        })
        // const a = document.createElement('a');
        // a.href = this.snapshotUrl;
        // a.download = 'snapshot.jpg';
        // a.click();
      }
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



