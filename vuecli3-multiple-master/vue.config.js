module.exports = {
  pages: {
    console: 'src/modules/console/console.js',

    // 只有entry属性时，直接用字符串表示模块入口
    client: {
      entry: 'src/modules/client/client.js',
      title: 'client page',
    },
    index : 'src/main.js'


  },
  productionSourceMap: false


}
