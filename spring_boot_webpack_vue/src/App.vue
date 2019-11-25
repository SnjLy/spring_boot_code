<template>
    <div id="test">
        {{content}}
    </div>
</template>

<script>
    export default {
        name: "App",
        data() {
            return {
                content: '组件实体'
            }
        }
    }
</script>

<style>
#test{
  color: red;
}

</style>