# Android 基础类库

该库封装了Android开发中常用的基础类，在开始新项目开发时，依赖该库可以减少基础配置和重复代码，专注于核心业务逻辑的开发。<br/>
该库并没有提供网络请求、数据库等功能的封装，只提供了一些基础的类和功能，可自行根据项目的需求进行扩展。

## 功能模块

- **BaseVMViewBindingActivity**：封装基础的 `Activity` 类，实现常用的生命周期管理和UI初始化。
- **BaseVMViewBindingFragment**：提供基础的 `Fragment` 封装，简化 `Fragment` 的使用。
- **ui**：提供了一个app升级功能的完整功能。
- **adapter**：提供基础的 `ListAdapter` 封装，简化 `ListAdapter` 的使用。

## 开始使用

1. 在项目根目录 `build.gradle` 文件中添加仓库地址：

```groovy
	dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}
```

2. 在项目的 `build.gradle` 文件中添加依赖：

```groovy
   implementation 'com.github.zmJoemi:biolerplate-arch:v1.0.0'
```