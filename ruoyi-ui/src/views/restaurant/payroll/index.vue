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
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-if="refreshTable" v-loading="loading" :data="tPayrollList" row-key="id" :default-expand-all="isExpandAll"
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column prop="userName" label="用户名称" width="200"></el-table-column>
      <el-table-column prop="payroll" label="工资" width="200"></el-table-column>
      <el-table-column prop="createTime" label="日期" width="200"></el-table-column>
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
          <el-input-number v-model="form.payroll" placeholder="份数" :precision='1' :min="0"></el-input-number>
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
import { listTPayroll } from "@/api/restaurant/payroll"

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
      this.add = true
      this.open = true
    },
    handleUpdate() {
      this.add = false
      this.open = true
    },
    handleDelete() {

    },
    handleExport() {

    },
    handleQuery() {

    },
    resetQuery() {

    },
    handleSelectionChange() {

    },
    submitForm() {
      this.open = false
    },
    cancel() {
      this.open = false
    }
  }
}
</script>

<style></style>