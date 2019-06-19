import request from '@/utils/request'
const tokens = {
  admin: {
    token: 'admin-token'
  },
  editor: {
    token: 'editor-token'
  }
}

const users = {
  'admin-token': {
    roles: ['admin'],
    introduction: 'I am a super administrator',
    avatar:
      'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',
    name: 'Super Admin'
  },
  'editor-token': {
    roles: ['editor'],
    introduction: 'I am an editor',
    avatar:
      'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',
    name: 'Normal Editor'
  }
}
export function login(data) {
  console.log(data.username.toString())
  console.log(data.password.toString())
  //此時換成後端 method
  // this.axios
  //   .post("http://localhost:9990/httpMethod", {
  //     name: this.$refs.username.value,
  //     pwd: this.$refs.password.value
  //   })
  //   .then(function (response) {
  //     //currentObj.output = response.data;
  //     console.log(response.data);
  //     status = response.data;
  //     if (status == "success") {
  //       nowthis.$refs.loginForm.validate(valid => {
  //         if (valid) {
  //           nowthis.loading = true;
  //           nowthis.$store
  //             .dispatch("user/login", nowthis.loginForm)
  //             .then(() => {
  //               nowthis.$router.push({
  //                 path: nowthis.redirect || "/",
  //                 query: nowthis.otherQuery
  //               });
  //               nowthis.loading = false;
  //             })
  //             .catch(() => {
  //               nowthis.loading = false;
  //             });
  //         } else {
  //           console.log("error submit!!");
  //           return false;
  //         }
  //       });
  //     }
  //   })
  //   .catch(function (error) {
  //     //currentObj.output = error;
  //     console.log(error);
  //     status = "fail";
  //     //alert(error);
  //   });

  // {
  //   "code": 20000,
  //     "data": {
  //     "roles": [
  //       "admin"
  //     ],
  //       "introduction": "I am a super administrator",
  //         "avatar": "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif",
  //           "name": "Super Admin"
  //   }
  // }

  // {
  //   "code": 20000,
  //     "data": {
  //     "roles": [
  //       "admin"
  //     ],
  //       "introduction": "I am a super administrator",
  //         "avatar": "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif",
  //           "name": "Super Admin"
  //   }
  // }

  return request({
    url: '/httpMethod',
    method: 'post',
    data: {
      name: data.username,
      pwd: data.password
    }
  })
}

export function getInfo(token) {
  return request({
    url: '/user/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/user/logout',
    method: 'post'
  })
}
