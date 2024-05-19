# 目标达人 App

![Gradle](https://img.shields.io/badge/gradle-8.4-02303A.svg)
![Compose](https://img.shields.io/badge/compose-used-6200EA.svg)

## 二维码

| release                                                                                                                                           | debug                                                                                                                                           |
|---------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------|
| <img width="300" src="https://yiwen-oss.oss-cn-shanghai.aliyuncs.com/httpsgithub.comHao-yiwenGoalManreleasesdownloadv1.0.2app-release.apk.png" /> | <img width="300" src="https://yiwen-oss.oss-cn-shanghai.aliyuncs.com/httpsgithub.comHao-yiwenGoalManreleasesdownloadv1.0.2app-debug.apk.png" /> |

通过设置每日目标打开每日工作学习生活!!!

## 阶段成果

- 完成 ✅
- 未完成：❎
- 进行中: 🕙

### 1.0.0 ✅

- 项目基础功能基本完成
    - 添加目标 ✅
    - 重置目标 ✅
    - 每天晚上 24 点自动重置目标 ✅
    - 本地存储目标 ✅
- 项目在 github 自动构建 ✅

### 1.1.0 🕙

- 项目打卡
    - 当天事件完成数大于等于目标数的 20%可以进行打卡
        - ✅ 功能：已完成 2024-04-24
    - 存储一个完成百分比，此百分比会在热力图中用到
        - ✅ 功能：在热力图中展示每天完成情况，根据完成百分比来标识热力图的颜色
    - ✅ 打卡天数
- 使用 widget 快捷展示目标
    - ✅ 你已经打卡多少天
- ✅ 目标左滑删除功能
- ❎ splashscreen 功能
    - 经过调研，没有必要使用activity添加splash screen，当前splash api只支持图标动画
- 按周打卡统计功能
    - 添加一个第几周字段
- 按月打开统计功能
    - 添加一个第几个月字段
- 按年统计
    - 添加一个第几年字段

### 1.2.0

- 目标分享，分享时候可以生成图片分享
- 使用服务存储用户数据
- 用户模块
- 应用须知
- android 上线

### 1.5.0

- 使用 flutter 完成 ios 端开发
- app 正式上线
- 服务端进行数据分析
- ios 上线

## BUG 修复

## 1.0.0

- 每日情况目标重置时机设置的是每天晚上 12 点，但是实际上是每次打开 app 时候重置的，需要修复

## 1.1.0

- 打卡逻辑
    - ~~goal 表只存储从创建和删除的所有目标，删除的用 status=3 表示~~
    - ~~
      goal_record
      表存储每天的打卡记录，如果当天打卡了，就在这个表中插入一条记录，如果没有打卡，就不插入记录~~
    - ~~当用户重新打卡后，更新表中的值~~
        - ~~获取更新时间为今天的 goal，然后根据 goal 更新 goal_record 表中的数据~~
            - ~~
              之所以这样做是因为需要更新今天删除的 goal 的打卡记录，如果只是获取 currentGoal
              则无法获取到今天删除的 goal~~
    - ~~
      使用新的逻辑后，每天的定时任务需要更改逻辑，需要从将 goal 中所有 status 更改为 1 变成只讲状态是
      2 的更改为 1~~
