<template>
  <div>
    <form v-if="!isReg">
      <div id="login">
        <p>用户名:
          <input type="text" v-model="name" placeholder="用户名" @blur="checkName()">
          <span>{{msgName}}</span>
        </p>
        <p>密码:
          <input @keyup="checkpassword()" type="password" v-model="password" placeholder="密码">
          <span>{{msgPassword}}</span>
        </p>
        <p>
          <input type="button" v-model="btn" @click="codebtn()">
          <input type="text" placeholder="验证码">
          <span></span>
        </p>
        <p>
          <input type="checkbox" v-model="check" @click="checkagree()">同意"服务条款"和"隐私权相关政策"
          <span>{{msgAgree}}</span>
        </p>
        <hr>
        <p>
          <input type="submit" value="登录" @click="login($event)">
        </p>
      </div>
    </form>

    <form v-else="isReg">
      <div id="registry">
        <p>用户名:
          <input type="text" v-model="name" placeholder="用户名" @blur="checkName()">
          <span>{{msgName}}</span>
        </p>
        <p>密码:
          <input @keyup="checkpassword()" type="password" v-model="password" placeholder="密码">
        </p>
        <p>重复输入密码:
          <input @keyup="checkpassword()" type="password" v-model="repPassword" placeholder="密码">
          <span>{{msgPassword}}</span>
        </p>
        <p>
          <input type="button" v-model="btn" @click="codebtn()">
          <input type="text" placeholder="验证码">
          <span></span>
        </p>
        <p>
          <input type="checkbox" v-model="check" @click="checkagree()">同意"服务条款"和"隐私权相关政策"
          <span>{{msgAgree}}</span>
        </p>
        <hr>
        <p>
          <input type="submit" value="注册" @click="register($event)">
        </p>
      </div>
    </form>
  </div>
</template>

<script>
  export default {
    name: "Login",
    data() {
      return {
        isReg: false,
        name: "",
        password: "",
        repPassword: "",
        msgName: "",
        check: true,
        msgPassword: "",
        msgAgree: "",
        btn: "获取验证码",
        disab: false,
        c: 60
      }
    },
    methods: {
      checkName: function () {
        if (this.name.length == 0) {
          this.msgName = "用户名不能为空";
        } else if (this.name.length < 2) {
          this.msgName = "用户名至少2个字符";
        } else {
          this.msgName = "";
        }
        return this.name > 2;
      },
      checkpassword: function () {
        var word = this.password.trim();
        if (word.length == 0)
          this.msgPassword = "密码不能为空";
        var count = 0;
        if (/[0-9]/.test(word)) {
          count++;
        }
        if (/[A-Za-z]/.test(word)) {
          count++;
        }
        if (/[^0-9A-Za-z]/.test(word)) {
          count++;
        }
        if (count == 3 && word.length >= 6) {
          this.msgPassword = "高强度";
        } else if (count == 2 && word.length >= 6) {
          this.msgPassword = "中强度";
        } else {
          this.msgPassword = "低强度";
        }
        return word >= 0;
      },
      codebtn: function () {
        console.log(1);
        this.disab = true;
        setTimeout(this.enableCodeBtn, 1000);
      },
      enableCodeBtn: function () {
        if (this.c > 0) {
          this.btn = this.c + "秒后重新获取";
          setTimeout(this.enableCodeBtn, 1000); // 1 秒后再次调用自己
          this.c--;
        } else {
          this.disab = false;
          this.btn = "获取短信验证码";
          this.c = 60;
        }
      },
      checkagree: function () {
        if (!this.check) {
          this.msgAgree = "";
        } else {
          this.msgAgree = "必须勾选";
        }
        return this.check;
      },
      register: function (event) {
        if (this.checkName() & this.checkpassword() & this.checkagree()) {
          this.$router.push("/home")
        } else {
          console.log(1);
          event.preventDefault();
        }
      },
      login: function (event) {
        this.isReg=true,
        this.$router.push("/home")
      }
    }
  }
</script>

<style scoped>

</style>
