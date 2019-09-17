<template>
  <div>
    <ul>
      <li v-for="(item,index) in pageLists"
          v-bind:key="index">
<!--        title: {{item.title}}<br>-->
<!--        content: {{item.content}}-->
        {{item.title}} -- {{item.content}}
      </li>
    </ul>
  </div>
</template>

<script>
  import store from '@/store'

  export default {
    name: "List",
    store,
    computed: {
      pageLists() {
        return store.state.lists
      }
    }
  }
</script>

<style scoped>

</style>
