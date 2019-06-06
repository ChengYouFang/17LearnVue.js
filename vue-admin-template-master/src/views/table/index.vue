<template>
  <div class="app-container">
    <el-table
      ref="singleTable"
      oncontextmenu="return false"
      v-loading="listLoading"
      :data="filtermethod(list,search)"
      element-loading-text="Loading"
      fit
      highlight-current-row
      @selection-change="handleSelectionChange"
      @current-change="handleCurrentChange"
      @cell-mouse-enter="openDetails"
      @row-contextmenu="showmenu($row,$event, { name: item, index })"
    >
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column align="center" label="ID" width="95" sortable prop="id">
        <template slot-scope="scope">{{ scope.row.id }}</template>
      </el-table-column>
      <el-table-column label="Title" sortable prop="name">
        <template slot-scope="scope">{{ scope.row.title }}</template>
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
          <span>{{ scope.row.display_time }}</span>
        </template>

        <template slot="header" slot-scope="scope">
          <el-input v-model="search" size="mini" placeholder="請輸入要搜尋文字"/>
        </template>
      </el-table-column>
    </el-table>

    <vue-context ref="menu">
      <template slot-scope="child" v-if="child.data">
        <li>
          <a
            class="textder"
            href="#"
            @click.prevent="alertName"
          >Alert name:{{currentRow.title}} status:{{ currentRow.status }}</a>
        </li>
        <li>
          <a class="textder" href="#" @click.prevent="remove( list)">Delete</a>
        </li>
      </template>
      <span>{{ result1 }}</span>
    </vue-context>
    <div style="margin-top: 20px">
      <el-button @click="toggleSelection([list[1], list[2]])">切换第二、第三行的选中状态</el-button>
      <el-button @click="toggleSelection()">取消选择</el-button>
    </div>
  </div>
</template>

<script>
import { getList } from "@/api/table";
import { VueContext } from "vue-context";
export default {
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
    }
  },

  data() {
    return {
      testdata: null,
      result1: null,
      result2: null,
      list: null,
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
      value6: ""
    };
  },
  created() {
    this.fetchData();
  },
  methods: {
    fetchData() {
      this.listLoading = true;
      getList().then(response => {
        this.list = response.data.items;
        this.listLoading = false;
      });
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
      var x = e.pageX;
      var y = e.pageY;

      this.result1 = x;
      this.result2 = y;
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
      for (var i = 0; i < 10; i++) {
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
              if (newspkey[j].toString() == "title") {
                pass = newsp[j] == new_serach[i];
              } else if (newspkey[j].toString() == "status") {
                pass = newsp[j].toString().indexOf(new_serach[i]) >= 0 || pass;
              }

              // if (newsp[j].toString().indexOf(new_serach[i]) >= 0) pass = true;
            }
            if (pass) new_data.push(data);
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
    }
  },
  mounted() {
    window.addEventListener("mousemove", this.mouseIsMoving);
  }, //mounted

  destroyed: function() {
    window.removeEventListener("mousemove", this.mouseIsMoving);
  }
};
</script>
