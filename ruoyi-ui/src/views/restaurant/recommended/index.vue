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
          v-hasPermi="['restaurant:recommended:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['restaurant:recommended:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['restaurant:recommended:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['restaurant:recommended:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-if="refreshTable" v-loading="loading" :data="tRecommendedList" row-key="id"
      :default-expand-all="isExpandAll" :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
      @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column prop="dishName" label="菜品名称" width="170"></el-table-column>
      <el-table-column prop="number" label="菜品销量" width="170"></el-table-column>
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

    <el-dialog title="进菜管理" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="选择菜品">
          <el-cascader v-model="form.selector" :options="form.options" :style="{ width: '100%' }" placeholder="选择菜品"
            clearable></el-cascader>
        </el-form-item>
        <el-form-item label="菜品销量" prop="number">
          <el-input-number v-model="form.number" controls-position="right" :min="0" :precision='0' />
        </el-form-item>
        <el-form-item label="销售时间" prop="dateTime">
          <el-date-picker v-model="form.dateTime" value-format="yyyy-MM-dd" placeholder="选择日期">
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
import { list, add, selectTSalestableListById, edit, remove } from "@/api/restaurant/recommended"
import { listPrice } from "@/api/restaurant/fixPrice"
import { listMenuTree } from "@/api/restaurant/menu"
export default {
  dicts: ['t_plate_color'],
  data() {
    return {
      queryParams: {
        tenantId: 1,
        pageNum: 1,
        pageSize: 10,
        dishName: undefined,
        createTime: undefined
      },
      dishNames: [],
      createTimes: [],
      showSearch: true,
      form: {
        number: 0,
        selector: [],
        options: [],
        dateTime: undefined
      },
      loading: true,
      total: 0,
      dateRange: [],
      multiple: true,
      single: true,
      refreshTable: true,
      tRecommendedList: [],
      isExpandAll: true,
      open: false,
      add: true,
      ids: [],
      rules: {
        number: [
          { required: true, message: "菜品数量不能为空", trigger: "blur" }
        ],
        dateTime: [
          { required: true, message: "销售时间不能为空", trigger: "blur" }
        ]
      }
    }
  },
  created() {
    this.getList()
    this.getPrice()
  },
  methods: {
    getList() {
      this.loading = true;
      list(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.tRecommendedList = response.rows
        this.loading = false
        this.total = response.total
      })
    },
    getPrice() {
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
          this.form.options = list
        })
      })
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
      this.dishNames = selection.map(item => item.dishName)
      this.createTimes = selection.map(item => item.createTime)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    submitForm() {
      this.form.isState = 1
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.number > 0) {
            if (this.add) {
              var dishName = undefined
              if (this.form.selector.length > 0) {
                for (var i = 0; i < this.form.options[this.form.selector[0]].tMenus.length; i++) {
                  if (this.form.options[this.form.selector[0]].tMenus[i].id == this.form.selector[1]) {
                    dishName = this.form.options[this.form.selector[0]].tMenus[i].dishName
                    break
                  }
                }
                var dataform = {
                  isState: this.form.isState,
                  number: this.form.number,
                  dishId: this.form.selector[1],
                  dishName: dishName,
                  createTime: this.form.dateTime
                }
                add(dataform).then(res => {
                  if (res.data == -1) {
                    this.$modal.msgError("选择的日期下已有该菜品")
                  } else if (res.data == 0) {
                    this.$modal.msgError("添加失败")
                  } else if (res.data == 1) {
                    this.$modal.msgSuccess("添加成功")
                    this.getList()
                  }
                })
              }
            } else {
              for (var i = 0; i < this.form.options[this.form.selector[0]].tMenus.length; i++) {
                if (this.form.options[this.form.selector[0]].tMenus[i].id == this.form.selector[1]) {
                  dishName = this.form.options[this.form.selector[0]].tMenus[i].dishName
                  break
                }
              }
              var dataform = {
                isState: this.form.isState,
                number: this.form.number,
                dishId: this.form.selector[1],
                dishName: dishName,
                createTime: this.form.dateTime,
                id: this.form.id
              }
              edit(dataform).then(res => {
                if (res.data == -1) {
                  this.$modal.msgError("该日期已存在该种菜品，请在相应日期相应菜品下修改")
                } else if (res.data == 0) {
                  this.$modal.msgError("修改失败")
                } else if (res.data == 1) {
                  this.$modal.msgSuccess("修改成功")
                  this.getList()
                }
              })
            }
          } else {
            this.$modal.msgError("请填写正确的数量")
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
    handleAdd() {
      this.add = true
      this.reset()
      this.open = true;
    },
    handleUpdate(row) {
      this.add = false
      this.reset()
      this.open = true;
      const id = row.id || this.ids
      selectTSalestableListById(id).then(res => {
        this.form.id = res.data[0].id
        this.form.number = res.data[0].number
        this.form.dateTime = res.data[0].createTime
        for (var i = 0; i < this.form.options.length; i++) {
          for (var j = 0; j < this.form.options[i].tMenus.length; j++) {
            if (this.form.options[i].tMenus[j].id == res.data[0].dishId) {
              this.form.selector = [i, res.data[0].dishId]
              break
            }
          }
        }
      })
    },
    handleDelete(row) {
      var that = this
      const ids = row.id || this.ids
      const dishNames = row.dishName || this.dishNames
      const createTimes = row.createTime || this.createTimes
      if (row.id == 0) {
        ids.push(0)
      }
      var str = ""
      var listOrInt = 1
      for (var i = 0; i < ids.length; i++) {
        console.log(this.queryParams[ids[i]])
        if (i == ids.length - 1) {
          str = str + "日期为:" + createTimes[i] + "的" + dishNames[i]
        } else {
          str = str + "日期为:" + createTimes[i] + "的" + dishNames[i] + ","
        }
      }
      if (str == "") {
        listOrInt = 0
        str = "日期为:" + createTimes + "的" + dishNames
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
              resStr = resStr + "日期为" + res.data.ids[i].createTime + "的" + res.data.ids[i].dishName + ",";
            }
            that.$modal.msgError("删除" + resStr + "失败")
          }
          that.getList()
        })
      })
    },
    handleExport() {
      this.download('/restaurant/recommended/export', {
        ...this.queryParams
      }, `进菜管理_${new Date().getTime()}.xlsx`)
    },
    reset() {
      this.form.number = 0
      this.form.selector = []
      this.form.dateTime = undefined
      this.resetForm("form")
    },
  }
}
</script>
  
<style></style>