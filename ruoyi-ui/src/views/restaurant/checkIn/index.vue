<template>
  <div class="container">
    <el-button class="checkin" type="primary" @click="handleCheckIn">{{ checkInTitile }}
    </el-button>
  </div>
</template>
  
<script>
import Cookies from "js-cookie";
import { add } from "@/api/restaurant/checkIn"
var time
export default {
  data() {
    return {
      form: {},
      checkInTitile: "",
      checkInTasks: null
    }
  },
  mounted() {
    time = new Date().getHours()
    if (time >= 8 && time <= 10) {
      this.checkInTitile = "上班打卡"
    } else if (time >= 19 && time <= 21) {
      this.checkInTitile = "下班打卡"
    } else {
      this.checkInTitile = "未到打卡时间"
    }
    this.checkInTasks = setInterval(() => {
      this.getcheckInTasks()
    }, 5 * 1000)
  },
  beforeRouteLeave(to, from, next) {
    clearInterval(this.checkInTasks)
    this.checkInTasks = null
    next()
  },
  methods: {
    getcheckInTasks() {
      time = new Date().getHours()
      if (time >= 8 && time <= 10) {
        this.checkInTitile = "上班打卡"
      } else if (time >= 19 && time <= 21) {
        this.checkInTitile = "下班打卡"
      } else {
        this.checkInTitile = "未到打卡时间"
      }
    },
    handleCheckIn() {
      this.form = {}
      var timeState = 2
      if (time >= 8 && time <= 10) {
        timeState = 0
      } else if (time >= 19 && time <= 21) {
        timeState = 1
      } else {
        timeState = 2
      }
      if (timeState == 2) {
        this.$modal.msgError("未到打卡时间")
      } else {
        this.form.userName = Cookies.get("username"),
        this.form.attendanceType = timeState,
        this.form.isState = 1
        add(this.form).then(res => {
          if(res.data == -1){
            this.$modal.msgError("请勿重复打卡")
          }else if(res.data == 0){
            this.$modal.msgSuccess("打卡失败，请重试")
          }else if(res.data == 1){
            this.$modal.msgSuccess("打卡成功")
          }
        })
        
      }

    }
  }
}
</script>

<style>
.container {
  width: 100%;
  height: 80vh;
  display: flex;
  align-items: center;
  justify-content: center;
}

.checkin {
  height: 20vw;
  width: 20vw;
  border-radius: 100%;
  color: #ffffff;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 35px;
  font-weight: 500;
  font-family: 'Courier New', Courier, monospace;
  box-shadow: 10px 10px 5px #888888;
}
</style>