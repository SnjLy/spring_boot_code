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

<style lang="css">
#test {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: red;
}

</style>