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
          <div
            style="height: 30%;width: 100%;display: flex;flex-direction: column;align-items: center;justify-content: center;">
            <div v-for="(item, index) in objectList" :key="index" :label="item.index" style="font-size: 20;">
              {{ dict.type.t_plate_color[item.dictValue].label }}:{{ item.number }}==>{{ item.price }}(元) x
              {{ item.number }}(份) = {{ item.price * item.number }}(元)
            </div>
          </div>
          <div
            style="height: 15%;width: 100%;display: flex;flex-direction: row;align-items: center;justify-content: center;">
            <div v-show="priceDisplay" style="font-size: 50;font-weight: 600;">
              总计:<el-input v-model="price" style="width: 100px;" />元
            </div>
            <el-button v-show="priceDisplay" @click="slectDishName" style="margin-left: 20px;">选择菜品</el-button>
            <el-button v-show="priceDisplay" @click="payMe" style="margin-left: 20px;">付款</el-button>
          </div>
        </div>
      </div>
    </div>

    <el-dialog title="支付" :visible.sync="open" width="500px" append-to-body>
      <div style="width: 100%;display: flex;align-items: center;justify-content: center;">
        <img style="height: 260px;" src="../../../assets/images/payMe.png" />
      </div>
      <div
        style="width: 100%;height: 50px; display: flex;align-items: center;justify-content: center;font-size: 30px;font-weight: 600;">
        请支付{{ price }}元
      </div>
      <div style="height: 50px;width: 100%;display: flex;align-items: center;justify-content: center;">
        <el-button type="primary" @click="submitForm">支付成功</el-button>
        <el-button @click="cancel">取消支付</el-button>
      </div>
    </el-dialog>

    <el-dialog title="选择售出的菜品" :visible.sync="dishNameDialog" width="500px" append-to-body>
      <el-form ref="elForm" size="medium">
        <el-row v-for="(item, index) in formData" :key="index">
          <el-col :span="12">
            <el-form-item label="选择菜品">
              <el-cascader v-model="item.selector" :options="item.options" :style="{ width: '100%' }" placeholder="选择菜品"
                clearable></el-cascader>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="份数">
              <el-input-number v-model="item.number" placeholder="份数" :precision='0' :min="0"></el-input-number>
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>
      <div style="height: 50px;width: 100%;display: flex;align-items: center;justify-content: center;">
        <el-button type="primary" @click="submitForm_dish">确定</el-button>
        <el-button @click="cancel_dish">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { upload, addList, listMenuTree, listPrice } from "@/api/identify/identify"
// import { listPrice } from "@/api/restaurant/fixPrice"
export default {
  dicts: ['t_plate_color'],
  data() {
    return {
      formData: [
        {
          number: 0,
          selector: [],
          options: []
        }, {
          number: 0,
          selector: [],
          options: []
        }, {
          number: 0,
          selector: [],
          options: []
        }, {
          number: 0,
          selector: [],
          options: []
        }, {
          number: 0,
          selector: [],
          options: []
        }
      ],
      dishNameDialog: false,
      open: false,
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
      objectList: [],
      TPriceList: [],
      price: 0,
      priceDisplay: false,
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
      queryParams: {
        tenantId: 1,
        pageNum: 1,
        pageSize: 10,
        dishName: ''
      },
    }
  },
  mounted() {
    this.videoElement = this.$refs.videoElement;
    this.canvasElement = this.$refs.canvasElement;
  },
  created() {
    this.getList();
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
      listPrice(this.queryParams).then(response => {
        listMenuTree(response.rows).then(response => {
          var list = response.data
          for (var i = 0; i < list.length; i++) {
            for (var j = 0; j < list[i].tMenus.length; j++) {
              list[i].tMenus[j].label = list[i].tMenus[j].dishName
              list[i].tMenus[j].value = list[i].tMenus[j].id
            }
            list[i].children = list[i].tMenus
            list[i].value = list[i].plateColor
            list[i].label = this.dict.type.t_plate_color[list[i].plateColor].label
          }
          this.TPriceList = list
          for (i = 0; i < this.formData.length; i++) {
            this.formData[i].options = this.TPriceList
          }
        })
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
              this.$modal.msgError("无法打开摄像头:" + err)
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
            this.price = 0
            var tp = 0
            var reslist = res.data.body.objectList
            for (var i = 0; i < this.TPriceList.length; i++) {
              for (var j = 0; j < reslist.length; j++) {
                if (reslist[j].dictValue == this.TPriceList[i].plateColor) {
                  reslist[j].price = this.TPriceList[i].platePrice
                  tp = tp + this.TPriceList[i].platePrice * reslist[j].number
                  break;
                }
              }
            }
            this.price = tp
            this.objectList = reslist
            var img_str = "data:image/png;base64," + res.data.body.base64
            var imgFile = this.base64ImgtoFile(img_str);
            let blob = new Blob([imgFile], { type: 'image/png' })
            let imageUrl = (window.URL || window.wekitURL).createObjectURL(blob)
            this.verificationImg = imageUrl
            this.priceDisplay = true
            // this.open = true
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
    submitForm() {
      this.open = false
    },
    cancel() {
      this.open = false
    },
    payMe() {
      this.open = true
    },
    slectDishName() {
      this.dishNameDialog = true
    },
    submitForm_dish: function () {
      var flag = 0
      var list = []
      var date = new Date();
      for (var i = 0; i < this.formData.length; i++) {
        if (this.formData[i].number != 0) {
          list.push({
            number: this.formData[i].number,
            dishId: this.formData[i].selector[1],
            isState: 1,
            createTime: date
          })
          flag = 1
        }
      }
      if (flag == 1) {
        addList(list).then(res => {
          if (res.data != 0) {
            this.$modal.msgError("添加失败")
          } else {
            this.$modal.msgSuccess("添加成功")
          }
        })
      }
      this.dishNameDialog = false
    },
    cancel_dish() {
      this.dishNameDialog = false
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



