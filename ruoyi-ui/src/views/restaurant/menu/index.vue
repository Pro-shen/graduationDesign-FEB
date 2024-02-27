<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="菜品名称" prop="dishName">
        <el-input v-model="queryParams.dishName" placeholder="请输入菜品名称" clearable style="width: 240px"
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
          v-hasPermi="['restaurant:menu:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['restaurant:menu:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['restaurant:menu:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['restaurant:menu:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-if="refreshTable" v-loading="loading" :data="tMenuList" row-key="id" :default-expand-all="isExpandAll"
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column prop="dishName" label="菜品名称" width="170"></el-table-column>
      <el-table-column label="盘子颜色" width="170" align="center">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.t_plate_color" :value="scope.row.plateColor" />
        </template>
      </el-table-column>
      <el-table-column prop="dishStock" label="菜品存量" width="170"></el-table-column>
      <!-- <el-table-column prop="dishUsed" label="菜品销量" width="170"></el-table-column> -->
      <el-table-column prop="createTime" label="创建时间" width="170"></el-table-column>
      <el-table-column prop="updateTime" label="更新时间" width="170"></el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['restaurant:menu:edit']">修改</el-button>
          <el-button v-if="scope.row.parentId != 0" size="mini" type="text" icon="el-icon-delete"
            @click="handleDelete(scope.row)" v-hasPermi="['restaurant:menu:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改菜单配置对话框 -->
    <el-dialog title="菜单管理" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="菜品名称" prop="dishName">
          <el-input v-model="form.dishName" placeholder="请输入菜品名称" />
        </el-form-item>
        <el-form-item label="盘子颜色" prop="plateColor">
          <el-select v-model="form.plateColor" placeholder="请选择盘子颜色">
            <el-option v-for="dict in dict.type.t_plate_color" :key="dict.value" :label="dict.label"
              :value="Number(dict.value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="菜品存量" prop="dishStock">
          <el-input-number v-model="form.dishStock" controls-position="right" :min="0" />
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
import { listMenu, addMenu, selectTMenuListById, edit, remove } from "@/api/restaurant/menu"

export default {
  dicts: ['t_plate_color'],
  data() {
    return {
      total: 0,
      single: true,
      multiple: true,
      loading: true,
      showSearch: true,
      refreshTable: true,
      isExpandAll: true,
      add: true,
      ids: [],
      dishNames: [],
      open: false,
      tMenuList: [],
      dateRange: [],
      queryParams: {
        tenantId: 1,
        pageNum: 1,
        pageSize: 10,
        dishName: undefined,
        createTime: undefined
      },
      form: {},
      rules: {
        dishName: [
          { required: true, message: "菜品名称不能为空", trigger: "blur" }
        ],
        plateColor: [
          { required: true, message: "盘子颜色不能为空", trigger: "blur" }
        ],
        dishStock: [
          { required: true, message: "请输入正确的菜品存量(份)", trigger: "blur", pattern: /^-?[1-9]\d*$/, }
        ]
      }
    }
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.loading = true;
      listMenu(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.tMenuList = response.rows
        this.loading = false
        this.total = response.total
      })
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.dishNames = selection.map(item => item.dishName)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    handleAdd() {
      this.open = true
      this.add = true
      this.reset()
    },
    handleUpdate(row) {
      this.add = false
      this.reset()
      this.open = true
      const id = row.id || this.ids
      this.form.id = id
      selectTMenuListById(id).then(res => {
        this.form = res.data[0]
      })
    },
    submitForm: function () {
      this.form.isState = 1
      this.form.dishUsed = 0
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.add) {
            addMenu(this.form).then(res => {
              if (res.data == 0) {
                this.$modal.msgError("添加失败")
              } else if (res.data == 1) {
                this.$modal.msgSuccess("添加成功")
                this.getList();
              }
            })
          } else {
            edit(this.form).then(res => {
              if (res.data == 0) {
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
        this.open = false
      })
    },
    cancel() {
      this.open = false
    },
    handleDelete(row) {
      var that = this
      const ids = row.id || this.ids
      const dishNames = row.dishName || this.dishNames
      if (row.id == 0) {
        ids.push(0)
      }
      var str = ""
      var listOrInt = 1
      for (var i = 0; i < ids.length; i++) {
        console.log(this.queryParams[ids[i]])
        if (i == ids.length - 1) {
          str = str + dishNames[i]
        } else {
          str = str + dishNames[i] + ","
        }
      }
      if (str == "") {
        listOrInt = 0
        str = dishNames
      }
      this.$modal.confirm('是否确认删除菜品名字为"' + str + '"的数据项？').then(function () {
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
              resStr = resStr + res.data.ids[i].dishName + ",";
            }
            that.$modal.msgError("删除" + resStr + "失败")
          }
          that.getList()
        })
      })
    },
    handleExport() {
      this.download('/restaurant/menu/export', {
        ...this.queryParams
      }, `菜单_${new Date().getTime()}.xlsx`)
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
    reset() {
      this.form = {
        dishName: undefined,
        plateColor: undefined,
        dishStock: undefined,
        isState: undefined
      };
      this.resetForm("form");
    },
  }
}
</script>

<style></style>