import request from '@/utils/request'

export function getList(params) {
  console.log('test' + params.id)
  return request({
    url: '/httpMethod2',
    method: 'get',
    params
  })
  // return request({
  //   url: '/table/list',
  //   method: 'get',
  //   params
  // })
}
