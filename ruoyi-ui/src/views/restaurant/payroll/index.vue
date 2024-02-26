<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="用户名称" prop="userName">
        <el-input v-model="queryParams.userName" placeholder="请输入用户名称" clearable style="width: 240px"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker v-model="dateRange" style="width: 240px" value-format="yyyy-MM-dd" type="daterange"
          range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['restaurant:payroll:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['restaurant:payroll:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['restaurant:payroll:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['restaurant:payroll:export']">导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="info" plain icon="el-icon-download" size="mini" @click="handleRegular"
          v-hasPermi="['restaurant:payroll:timedTasks']">定时任务测试</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-if="refreshTable" v-loading="loading" :data="tPayrollList" row-key="id" :default-expand-all="isExpandAll"
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column prop="userName" label="用户名称" width="200"></el-table-column>
      <el-table-column prop="payroll" label="工资" width="200"></el-table-column>
      <el-table-column prop="createTime" label="日期" width="200"></el-table-column>
      <el-table-column prop="updateTime" label="更新日期" width="200"></el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['restaurant:payroll:edit']">修改</el-button>
          <el-button v-if="scope.row.parentId != 0" size="mini" type="text" icon="el-icon-delete"
            @click="handleDelete(scope.row)" v-hasPermi="['restaurant:payroll:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <el-dialog title="工资管理" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="用户名字" prop="userName">
          <el-input v-model="form.userName" placeholder="用户名字" />
        </el-form-item>
        <el-form-item label="工资" prop="payroll">
          <el-input-number v-model="form.payroll" placeholder="工资" :precision='1' :min="0"></el-input-number>
        </el-form-item>
        <el-form-item label="工资日期" prop="createTime">
          <el-date-picker v-model="form.createTime" value-format="yyyy-MM-dd" placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { listTPayroll, add, selectTPayrollListById, edit, remove, selectTPayrollListByDay } from "@/api/restaurant/payroll"

export default {
  data() {
    return {
      add: true,
      open: false,
      total: 0,
      loading: true,
      showSearch: true,
      single: true,
      multiple: true,
      refreshTable: true,
      isExpandAll: true,
      queryParams: {
        tenantId: 1,
        pageNum: 1,
        pageSize: 10,
        dishName: undefined,
        createTime: undefined
      },
      dateRange: [],
      form: {},
      tPayrollList: [],
      ids: [],
      userNames: [],
      createTimes: [],
      rules: {
        userName: [
          { required: true, message: "用户名称不能为空", trigger: "blur" }
        ],
        payroll: [
          { required: true, message: "用户工资不能为空", trigger: "blur" }
        ],
        createTime: [
          { required: true, message: "工资时间不能为空", trigger: "blur" }
        ]
      }
    }
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      listTPayroll(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.tPayrollList = response.rows
        this.loading = false
        this.total = response.total
      })
    },
    handleAdd() {
      this.reset()
      this.add = true
      this.open = true
    },
    handleUpdate(row) {
      this.reset()
      this.add = false
      this.open = true
      const id = row.id || this.ids
      this.form.id = id
      selectTPayrollListById(id).then(res => {
        this.form = res.data[0]
      })
    },
    handleDelete(row) {
      var that = this
      const ids = row.id || this.ids
      const createTimes = row.createTime || this.createTimes
      const userNames = row.userName || this.userNames
      if (row.id == 0) {
        ids.push(0)
      }
      var str = ""
      var listOrInt = 1
      for (var i = 0; i < ids.length; i++) {
        if (i == ids.length - 1) {
          str = str + "用户名为:" + userNames[i] + ",日期为:" + createTimes[i]
        } else {
          str = str + "用户名为:" + userNames[i] + ",日期为:" + createTimes[i] + ","
        }
      }
      if (str == "") {
        listOrInt = 0
        str = "用户名为:" + userNames + ",日期为:" + createTimes
      }
      this.$modal.confirm('是否确认删除"' + str + '"的数据项？').then(function () {
        var data = {}
        if (listOrInt == 0) {
          data = {
            id: ids
          }
        } else {
          data = {
            ids: ids
          }
        }
        remove(data).then(res => {
          if (res.data.ids.length == 0) {
            that.$modal.msgSuccess("删除成功")
          } else {
            var resStr = ""
            for (i = 0; i < res.data.ids.length; i++) {
              resStr = resStr + "用户名为:" + res.data.ids[i].userName + ",日期为:" + res.data.ids[i].createTime + ",";
            }
            that.$modal.msgError("删除" + resStr + "失败")
          }
          that.getList()
        })
      })
    },
    handleExport() {
      this.download('/restaurant/payroll/export', {
        ...this.queryParams
      }, `日工资管理_${new Date().getTime()}.xlsx`)
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
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.userNames = selection.map(item => item.userName)
      this.createTimes = selection.map(item => item.createTime)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    submitForm() {
      this.form.isState = 1
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.add) {
            add(this.form).then(res => {
              if (res.data == -2) {
                this.$modal.msgError("用户名不存在")
              } else if (res.data == -1) {
                this.$modal.msgError("该日期下已有用户及其工资信息存在")
              } else if (res.data == 0) {
                this.$modal.msgError("添加失败")
              } else if (res.data == 1) {
                this.$modal.msgSuccess("添加成功")
                this.getList();
              }
            })
          } else {
            edit(this.form).then(res => {
              if (res.data == -2) {
                this.$modal.msgError("用户名不存在")
              } else if (res.data == -1) {
                this.$modal.msgError("该日期下已有用户及其工资信息存在")
              } else if (res.data == 0) {
                this.$modal.msgError("修改失败")
              } else if (res.data == 1) {
                this.$modal.msgSuccess("修改成功")
                this.getList();
              }
            })
          }
        } else {
          this.$modal.msgError("请填写正确的信息")
        }
      })
      this.open = false
    },
    cancel() {
      this.open = false
    },
    reset() {
      this.form = {
        userName: undefined,
        payroll: undefined,
        createTime: undefined,
        isState: undefined
      };
      this.resetForm("form");
    },
    handleRegular() {
      selectTPayrollListByDay().then(res => {
        console.log(res)
      })
    }
  }
}
</script>

<style></style>