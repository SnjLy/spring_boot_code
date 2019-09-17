<template>
  <div>
    <label>标题</label>
    <input type="text" v-model="title">
    <label>内容</label>
    <input type="text" v-model="content">
    <button type="button" @click="add()">添加</button>
  </div>
</template>

<script>
  import store from '@/store'

  export default {
    name: "Add",
    store,
    data() {
      return {
        title: '输入标题',
        content: '新闻内容'
      }
    },
    methods: {
      add() {
        store.commit('addItem', {
            title: this.title,
            content: this.content
          },
          this.title = '',
          this.content = '',
          this.$router.push('/home/list')
        )
      }
    }

  }
</script>

<style scoped>

</style>
