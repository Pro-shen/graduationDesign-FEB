<template>
  <div class="container">
    <div style="width: 50%;padding: 20px;display: flex;flex-direction: column;">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
        <el-form-item label="创建时间">
          <el-date-picker v-model="dateRange" style="width: 240px" value-format="yyyy-MM-dd" type="daterange"
            range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table v-if="refreshTable" v-loading="loading" :data="tCheckInList" row-key="id"
        :default-expand-all="isExpandAll" :tree-props="{ children: 'children', hasChildren: 'hasChildren' }">
        <el-table-column prop="userName" label="打卡人" width="180" align="center"></el-table-column>
        <el-table-column prop="createTime" label="打卡时间" width="180" align="center"></el-table-column>
        <el-table-column label="打卡类型" class-name="small-padding fixed-width" align="center">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.t_attendance_type" :value="scope.row.attendanceType
              " />
          </template>
        </el-table-column>
      </el-table>
      <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
        @pagination="getList" />
    </div>
    <div style="width: 50%;display: flex;align-items: center;justify-content: center;">
      <el-button class="checkin" type="primary" @click="handleCheckIn">{{ checkInTitile }}
      </el-button>
    </div>
  </div>
</template>
  
<script>
import Cookies from "js-cookie";
import { add, list } from "@/api/restaurant/checkIn"
var time
export default {
  dicts: ['t_attendance_type'],
  data() {
    return {
      showSearch: true,
      form: {},
      checkInTitile: "",
      checkInTasks: null,
      tCheckInList: [],
      dateRange: [],
      loading: false,
      refreshTable: true,
      isExpandAll: true,
      total: 0,
      queryParams: {
        createTime: new Date().getHours(),
        userName: Cookies.get("username"),
        tenantId: 1,
        pageNum: 1,
        pageSize: 10,
      },
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
  created() {
    this.getList();
  },
  beforeRouteLeave(to, from, next) {
    clearInterval(this.checkInTasks)
    this.checkInTasks = null
    next()
  },
  methods: {
    getList() {
      this.loading = true;
      list(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.tCheckInList = response.rows
        this.loading = false
        this.total = response.total
      })
    },
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
        this.form.userName = Cookies.get("username")
        this.form.attendanceType = timeState
        this.form.isState = 1
        add(this.form).then(res => {
          if (res.data == -1) {
            this.$modal.msgError("请勿重复打卡")
          } else if (res.data == 0) {
            this.$modal.msgSuccess("打卡失败，请重试")
          } else if (res.data == 1) {
            this.$modal.msgSuccess("打卡成功")
            this.getList()
          }
        })

      }

    },
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.queryParams.pageNum = 1;
      this.$refs.tables.sort(this.defaultSort.prop, this.defaultSort.order)
    }
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