Vue 前端教程

一，组件传值

父传子

```vue
<template>
	<div>
		<div >
			<url>
			<h1 v-for="item in my" >
				{{item.name}}  {{item.age}}
			</h1>
			</url>
		</div>
	</div>
</template>
<script>
	export default {
	  name: '',
	  props:["my"]
	}
</script>
```



```vue
<template>
  <div id="app">
  <!--  <img alt="Vue logo" src="./assets/logo.png">
    <HelloWorld msg="Welcome to Your Vue.js App"/> -->
	<my :my="list">
		<h1 slot="mySlot">我是插槽</h1>
	</my>
  </div>
</template>


<script>
import HelloWorld from './components/HelloWorld.vue'
import my from './components/my.vue'
export default {
  name: 'app',
  components: {
    HelloWorld,
	my
  },
  data() {
	  return {
		  list:[
		  		  {
		  			  name:"张三",
		  			  age: 19
		  		  },
		  		  {
		  		  			  name:"李四",
		  		  			  age: 166
		  		  }
		  ]
	  }
  }
}
</script>
```

子调用父方法（传个方法过去，在调用）

```
<template>
  <div id="app">
  <!--  <img alt="Vue logo" src="./assets/logo.png">
    <HelloWorld msg="Welcome to Your Vue.js App"/> -->
	<my :my="list" :add="add">
		<h1 slot="mySlot">我是插槽</h1>
	</my>

  </div>
</template>
<script>
import HelloWorld from './components/HelloWorld.vue'
import my from './components/my.vue'

export default {
  name: 'app',
  components: {
    HelloWorld,
	my
  },
  data() {
	  return {
		  list:[
		  		  {
		  			  name:"张三",
		  			  age: 19
		  		  },
		  		  {
		  		  			  name:"李四",
		  		  			  age: 166
		  		  }
		  ]
	  }
  },
  methods:{
	  add(list) {
		  this.list.push(list)
	  }
  }
}
</script>
<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
```

```
<template>
	<div>
	
		<div >
			<url>
			<h1 v-for="item in my" >
				{{item.name}}  {{item.age}}
			</h1>
			</url>
			<input v-model="msg">
			<button @click="addMsg">点击</button>
		</div>
	</div>
</template>
<script>
	export default {
	  name: '',
	  props:["my","add"],
	  data(){
		  return{
			  msg:''
		  }
	  },
	  methods:{
		  addMsg() {
			  const content = {name:this.msg,age:10}
			  this.add(content)
		  }
	  }
	}
</script>
```

子组件调用父组件的方法

 this.$emit("自定义方法名",content)

并且在父组件中必须自定义方法 @自定义方法名=“add"，才可以调用

二，插槽

对组件的扩展，

案例：

​	

```

```



三，路由



