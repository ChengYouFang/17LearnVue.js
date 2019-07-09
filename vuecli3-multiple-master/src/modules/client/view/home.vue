// :data="filtermethod(list,search)"
//:data="filteredData"
<template>
  <div class="app-container">
    <el-table
      ref="singleTable"
      oncontextmenu="return false"
      v-loading="listLoading"
      :data="filteredData"
      element-loading-text="Loading"
      fit
      highlight-current-row
      height="800"
      @current-change="handleCurrentChange"
      @cell-mouse-enter="openDetails"
      @cell-click="tmp"
      @row-contextmenu="showmenu($row,$event, { name: item, index })"
      :data-size="list"
    >
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column align="center" label="ID" width="95" sortable prop="id">
        <template slot-scope="scope">{{ scope.row.id }}</template>
      </el-table-column>
      <el-table-column label="Name" sortable prop="name">
        <template slot-scope="scope">
          <span class="link-type" @click="dialogFormVisible = true">{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Author" width="110" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.author }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Pageviews" width="110" align="center">
        <template slot-scope="scope">{{ scope.row.pageviews }}</template>
      </el-table-column>
      <el-table-column class-name="status-col" label="Status" width="110" align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status | statusFilter">{{ scope.row.status }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="city" width="120">
        <template scope="scope">
          <el-select v-model="list[scope.$index].city" placeholder="請選擇">
            <el-option
              v-for="item in cities"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
              <span style="float: left">{{ item.label }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{ item.value }}</span>
            </el-option>
          </el-select>
        </template>
      </el-table-column>

      <el-table-column align="center" prop="created_at" label="Display_time" width="200">
        <template slot-scope="scope">
          <i class="el-icon-time"/>
          <span>{{ scope.row.displaytime }}</span>
        </template>
      </el-table-column>

      <el-table-column min-width="300px" label="Title" prop="title">
        <template slot-scope="{row}">
          <template v-if="row.edit">
            <el-input v-model="row.title" class="edit-input" size="small"/>
            <el-button
              class="cancel-btn"
              size="small"
              icon="el-icon-refresh"
              type="warning"
              @click="cancelEdit(row)"
            >cancel</el-button>
          </template>
          <span v-else>{{ row.title }}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="Actions" width="200">
        <template slot-scope="{row}">
          <el-button
            v-if="row.edit"
            type="success"
            size="small"
            icon="el-icon-circle-check-outline"
            @click="confirmEdit(row)"
          >Ok</el-button>
          <el-button
            v-else
            type="primary"
            size="small"
            icon="el-icon-edit"
            @click="row.edit=!row.edit"
          >Edit</el-button>
        </template>
        <template slot="header" slot-scope="scope">
          <el-input v-model="search" size="mini" placeholder="請輸入要搜尋文字"/>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
      title="Edit"
      :close-on-click-modal="false"
      :visible.sync="dialogFormVisible"
      ref="testdialog"
    >
      <el-form :model="form">
        <el-form-item label="搜尋">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="院區">
          <el-select v-model="form.region" placeholder="請選擇院區">
            <el-option label="区域一" value="shanghai"></el-option>
            <el-option label="区域二" value="beijing"></el-option>
          </el-select>
        </el-form-item>
        <el-table :data="gridData">
          <el-table-column property="date" label="日期" width="150"></el-table-column>
          <el-table-column property="name" label="姓名" width="200"></el-table-column>
          <el-table-column property="address" label="地址"></el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="tmp2">确 定</el-button>
      </div>
    </el-dialog>

    <vue-context ref="menu">
      <template slot-scope="child" v-if="child.data">
        <li>
          <a class="textder" href="#" @click.prevent="alertName">
            <div class="lay1">Alert name : {{currentRow.name}}</div>
            <div class="lay1">Status : {{ currentRow.status }}</div>
            <div class="lay1">City : {{ currentRow.city }}</div>
            <div class="lay1">Title : {{ currentRow.title }}</div>
          </a>
        </li>
        <li>
          <a class="textder" href="#" @click.prevent="remove( list)">Delete</a>
        </li>
      </template>
      <span>{{ result1 }}</span>
    </vue-context>
    <div style="margin-top: 20px ">
      <el-button @click="reload">reload</el-button>
      <el-button @click="toggleSelection([list[1], list[2]])">切换第二、第三行的选中状态</el-button>
      <el-button @click="toggleSelection()">取消选择</el-button>
    </div>
  </div>
</template>

<script>
import { VueContext } from "vue-context";

export default {
  name: "loadmore",
  components: { VueContext },
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: "success",
        draft: "gray",
        deleted: "danger"
      };
      return statusMap[status];
    }
  },
  computed: {
    showReset() {
      return this.items.length < items.length;
    },
    filteredData() {
      return this.list.filter((item, index) => {
        if (index < this.currentStartIndex) {
          return false;
        } else if (index > this.currentEndIndex) {
          return false;
        } else {
          return true;
        }
      });
    }
  },

  data() {
    return {
      testdata: null,
      result1: null,
      result2: null,
      list: [],
      listLoading: true,
      currentRow: null,
      search: "",
      multipleSelection: [],
      cities: [
        {
          value: "Beijing",
          label: "北京"
        },
        {
          value: "Shanghai",
          label: "上海"
        },
        {
          value: "Nanjing",
          label: "南京"
        },
        {
          value: "Chengdu",
          label: "成都"
        },
        {
          value: "Shenzhen",
          label: "深圳"
        },
        {
          value: "Guangzhou",
          label: "广州"
        }
      ],
      gridData: [
        {
          date: "2016-05-02",
          name: "王小虎",
          address: "上海市普陀区金沙江路 1518 弄"
        },
        {
          date: "2016-05-04",
          name: "王小虎",
          address: "上海市普陀区金沙江路 1518 弄"
        },
        {
          date: "2016-05-01",
          name: "王小虎",
          address: "上海市普陀区金沙江路 1518 弄"
        },
        {
          date: "2016-05-03",
          name: "王小虎",
          address: "上海市普陀区金沙江路 1518 弄"
        }
      ],

      value6: "",
      dialogFormVisible: false,
      form: {
        name: "",
        region: "",
        date1: "",
        date2: "",
        delivery: false,
        type: [],
        resource: "",
        desc: ""
      },
      currentStartIndex: 0,
      currentEndIndex: 20
    };
  },
  created() {
    this.fetchData();
      document.onkeyup=function(e){
    var key=window.event.keyCode;
    if(key==13){
      alert('Enter up')
    }
  }
  },
  computed: {
    filteredData() {
      return this.list.filter((item, index) => {
        if (index < this.currentStartIndex) {
          return true;
        } else if (index > this.currentEndIndex) {
          console.log("teeeeeeeeeeeeeeee1");
          return false;
        } else {
          console.log("teeeeeeeeeeeeeeee2");
          return true;
        }
      });
    }
  },
  methods: {
    fetchData() {
      this.listLoading = true;

      this.getRequest("/httpMethod2", {
        id: this.currentStartIndex.toString()
      }).then(resp => {
        const items = resp.data.data.items;

        this.list = items.map(v => {
          this.$set(v, "edit", false); // https://vuejs.org/v2/guide/reactivity.html
          v.originalTitle = v.title; //  will be used when user click the cancel botton
          return v;
        });
        this.listLoading = false;
        this.list = this.list;
        this.filteredData;
      });

      // getList({
      //   id: this.currentStartIndex.toString()
      // }).then(response => {
      //   const items = response.data.items;

      //   this.list = items.map(v => {
      //     this.$set(v, "edit", false); // https://vuejs.org/v2/guide/reactivity.html
      //     v.originalTitle = v.title; //  will be used when user click the cancel botton
      //     return v;
      //   });
      //   this.listLoading = false;
      //   this.list = this.list;
      //   this.filteredData;
      // });
      // console.log(this.list);
    },
    alertName() {
      // event.preventDefault();
      // var x = event.clientX;
      // var y = event.clientY;

      // this.$refs.menu.positionMenu(x, y);
      // console.log(x, y);
      alert(`You clicked on "${this.testdata.title}"!`);
      // alert(`You clicked on "${name}"!`);
    },

    remove(list) {
      //console.log(this.currentRow.id);
      // console.log(this.testdata.title);
      // console.log(list);
      for (var i = 0; i < list.length; i++) {
        if (list[i].id == this.currentRow.id) {
          list.splice(i, 1);
        }
      }

      //this.$delete(this.items, index);
    },

    reset() {
      this.items = [...items];
    },
    mouseIsMoving(e) {
      setTimeout(() => {
        this.result1 = e.pageX;
        this.result2 = e.pageY;
      }, 100);
    },
    selectitem(item, id) {
      for (var i = 0; i < item.length; i++) {
        if (item[i] == id) {
          this.$refs.singleTable.setCurrentRow(i);
          break;
        }
      }
    },
    showmenu(row, event, data) {
      this.$refs.menu.data = data;
      this.$refs.menu.show = true;
      //  console.log(this.$refs.singleTable);
      // console.log(this.$refs.singleTable.data[this.testdata.id]);
      //this.selectitem(this.$refs.singleTable.data, id);
      let length = this.$refs.singleTable.data.length;
      for (var i = 0; i < length; i++) {
        if (this.$refs.singleTable.data[i].id == this.testdata.id) {
          this.$refs.singleTable.setCurrentRow(this.$refs.singleTable.data[i]);
          //   console.log("qwe");
          break;
        }
      }
      // this.$refs.singleTable.setCurrentRow(this.list[this.testdata.id]);
      this.$refs.menu.$nextTick(() => {
        this.$refs.menu.positionMenu(this.result2, this.result1);

        this.$refs.menu.$el.focus();
        this.$refs.menu.setItemRoles();

        if (this.$refs.menu.closeOnScroll) {
          this.$refs.menu.addScrollEventListener();
        }

        this.$refs.menu.$emit("open", event, this.data, this.top, this.left);
      });

      //alert(this.result1 + "," + this.result2);
    },

    openDetails(row, column, cell, event) {
      this.testdata = row;
      //  console.log(row);
    },
    filtermethod(val, search) {
      // val.filter(
      //   data =>
      //     !search || data.title.toLowerCase().includes(search.toLowerCase())
      // );

      let new_serach = search.split(" ");
      var new_data = [];
      //  console.log(tmp);
      if (!search == false) {
        val.filter(function(data) {
          let tmp = data;
          // var args = Array.from(data);
          // if (data.indexOf(search)) console.log(data);

          // console.log($.inArray((search, data)));
          // console.log(Object.values(data));

          let newsp = Object.values(data);
          let newspkey = Object.keys(data);
          let pass;
          for (let i = 0; i < new_serach.length; i++) {
            ///簡單搜尋
            // console.log(Object.values(data).indexOf(new_serach[i]));
            // if (Object.values(data).indexOf(new_serach[i]) >= 0) {
            //   pass = true;
            //簡單搜尋 ex

            for (let j = 0; j < newsp.length; j++) {
              // if (newsp[j].indexOf(new_serach[i]) >= 0) pass = true;
              // console.log(newsp[j]);

              //針對各個欄位 自訂義 篩選邏輯
              if (newspkey[j].toString() == "name") {
                pass = newsp[j] == new_serach[i];
              } else if (newspkey[j].toString() == "status") {
                pass = newsp[j].toString().indexOf(new_serach[i]) >= 0 || pass;
              } else if (newspkey[j].toString() == "title") {
                pass = newsp[j].toString().indexOf(new_serach[i]) >= 0;
              }
              if (new_data.includes(data) == false)
                if (pass) new_data.push(data);
              // if (newsp[j].toString().indexOf(new_serach[i]) >= 0) pass = true;
            }

            // newsp.forEach(function(element) {
            //   console.log(newsp);
            //   // if (element.indexOf(new_serach[i]) >= 0) pass = true;
            // });
          }

          //詳細搜尋 針對字串 不多做討論
          // let newsp = Object.values(data);
          // // for (let j = 0; j < newsp; j++)
          // //   if (newsp.indexOf(new_serach[i]) >= 0) {
          // //     pass = true;
          // //   }
          // newsp.forEach(function(element) {
          //   //console.log(element);
          //   for (var j = 0; j < element.length; j++) {
          //     // if (new_serach[i].indexOf(element.charAt(i)) >= 0) pass = true;
          //     console.log(new_serach[i]);
          //   }
          // });

          // for (var i = 0; i < data.length; i++) {
          //   new_data.push(data[i]);
          // }
          // data =>
          //   !search || data.title.toLowerCase().includes(tmp.toLowerCase());

          // console.log("test");
          // console.log(new_data);
          return pass;
        });
      }
      if (new_data.length == 0) return val;
      return new_data;
    },
    handleCurrentChange(val) {
      this.currentRow = val;

      // console.log(val.id);
    },
    toggleSelection(rows) {
      if (rows) {
        rows.forEach(row => {
          this.$refs.singleTable.toggleRowSelection(row);
          console.log(row);
        });
      } else {
        this.$refs.singleTable.clearSelection();
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
      console.log(this.multipleSelection);
    },
    cancelEdit(row) {
      row.title = row.originalTitle;
      row.edit = false;
      this.$message({
        message: "The title has been restored to the original value",
        type: "warning"
      });
    },
    confirmEdit(row) {
      row.edit = false;
      row.originalTitle = row.title;
      this.$message({
        message: "The title has been edited",
        type: "success"
      });
    },
    rowdblclcik(row, column, event) {
      if (row.edit == false) {
        row.edit = true;
        row.originalTitle = row.title;
        this.$message({
          message: "The title has been edited" + row.edit,
          type: "success"
        });
      }
    },
    handleUpdate(row) {
      this.temp = Object.assign({}, row); // copy obj
      this.temp.timestamp = new Date(this.temp.timestamp);
      this.dialogStatus = "update";
      this.dialogFormVisible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].clearValidate();
      });
    },
    tmp(row, column, cell, event) {
      console.log(cell);
      //console.log(row[column.property]);

      console.log(column.property);
      if (row.edit == false && column.property == "title") {
        row.edit = true;
        row.originalTitle = row.title;
        this.$message({
          message: "The title has been edited" + row.edit,
          type: "success"
        });
      }
    },
    tmp2() {
      this.$notify({
        title: "成功",
        message: "更新成功",
        type: "success",
        duration: 2000
      });
      this.dialogFormVisible = false;
    },
    reload() {
      this.fetchData();
    },

    getMoreLog() {
      console.log("scrollTop not");

      this.dom = this.$refs.singleTable.bodyWrapper;
      this.dom.scrollTop = this.dom.scrollTop - 100;
      this.currentStartIndex = this.currentStartIndex + 20;

      this.currentEndIndex = 0;
      this.fetchData();
    }
  },
  mounted() {
    window.addEventListener("mousemove", this.mouseIsMoving);
    // 获取需要绑定的table
    this.dom = this.$refs.singleTable.bodyWrapper;
    this.dom.addEventListener("scroll", () => {
      // 滚动距离
      let scrollTop = this.dom.scrollTop;
      // 变量windowHeight是可视区的高度
      let windowHeight = this.dom.clientHeight || this.dom.clientHeight;
      // 变量scrollHeight是滚动条的总高度
      let scrollHeight = this.dom.scrollHeight || this.dom.scrollHeight;
      if (scrollTop + windowHeight === scrollHeight) {
        // 获取到的不是全部数据 当滚动到底部 继续获取新的数据
        if (!this.allData) this.getMoreLog();
        console.log(
          "scrollTop",
          scrollTop + "windowHeight",
          windowHeight + "scrollHeight",
          scrollHeight
        );
      }
      console.log(
        "scrollTop",
        scrollTop + "windowHeight",
        windowHeight + "scrollHeight",
        scrollHeight
      );
    });
  }, //mounted

  destroyed: function() {
    window.removeEventListener("mousemove", this.mouseIsMoving);
  }
};
</script>

<style scoped>
.edit-input {
  padding-right: 100px;
}
.cancel-btn {
  position: absolute;
  right: 100px;
  top: 15px;
}
.link-type,
.link-type:focus {
  color: #337ab7;
  cursor: pointer;
}
</style>

xss

<style scoped>
.lay1 {
  width: 400px;
}
</style>

<style scoped>
.el-table__body-wrapper .el-table__row td {
  display: none;
}
.el-table__body-wrapper .el-table__row {
  height: 38px;
}
</style>