VIPShop - Minecraft VIP 商店插件

VIPShop 是一个为 Spigot 1.20.1 服务器设计的 Minecraft 插件，它提供了一个直观的 GUI 商店界面，允许玩家使用游戏内金币或点券购买不同时长的 VIP 权限。插件支持与 Vault、PlayerPoints 和 LuckPerms 等流行插件集成，并提供 PlaceholderAPI 占位符以显示 VIP 状态和到期时间。

功能特性

•
GUI 商店界面： 玩家通过 /getvip 命令打开商店界面。

•
多种购买选项： 支持金币和点券两种支付方式购买 VIP。

•
VIP 时长选择： 提供一周、一月和永久 VIP 购买选项。

•
经济系统集成： 无缝集成 Vault (金币) 和 PlayerPoints (点券) 经济系统。

•
权限管理集成： 通过 LuckPerms 管理玩家的 VIP 权限。

•
VIP 到期管理： 自动检查并移除过期 VIP 权限。

•
PlaceholderAPI 支持： 提供占位符 %vip_time%、%vip_remaining% 和 %vip_status%，可在其他插件中显示 VIP 信息。

•
高度可配置： 灵活的配置文件允许您自定义价格、消息和 VIP 组。

兼容性

•
Minecraft 版本： Spigot 1.20.1

前置插件

在安装 VIPShop 之前，请确保您的服务器已安装以下插件：

•
Vault: 用于金币经济。(SpigotMC 下载链接)

•
PlayerPoints: 用于点券经济。注意： 由于 Maven 仓库不稳定，您需要手动下载并放置此插件的 JAR 文件。建议从 Modrinth PlayerPoints 页面 下载 PlayerPoints-3.3.2.jar。

•
LuckPerms: 用于权限管理。(SpigotMC 下载链接)

•
PlaceholderAPI: 用于占位符支持。(SpigotMC 下载链接)

安装指南

1.
下载插件： 从 发布页面 下载最新版本的 VIPShop-X.X.X.jar 文件。

2.
放置前置插件： 将上述所有前置插件的 .jar 文件放置到您服务器的 plugins/ 文件夹中。

3.
放置 VIPShop 插件： 将下载的 VIPShop-X.X.X.jar 文件放置到您服务器的 plugins/ 文件夹中。

4.
启动服务器： 首次启动服务器，VIPShop 插件将自动生成 plugins/VIPShop/config.yml 和 plugins/VIPShop/data.yml 文件。

5.
配置插件： 根据您的需求编辑 plugins/VIPShop/config.yml 文件（详见下文的配置说明）。

6.
重载或重启： 修改配置后，建议重启服务器以确保所有更改生效。您也可以尝试使用 /reload 命令，但不推荐在生产环境中使用。

使用方法

•
打开商店 GUI： 玩家在游戏内输入命令 /getvip。

PlaceholderAPI 占位符

安装 PlaceholderAPI 后，您可以在其他支持 PlaceholderAPI 的插件中使用以下占位符来显示玩家的 VIP 信息：

•
%vip_time%: 显示玩家 VIP 的到期时间。如果 VIP 是永久的，则显示 “forever”。如果已过期，则显示“已过期”。

•
%vip_remaining%: 显示玩家 VIP 的剩余时间（例如：“10天5小时”、“30分钟”）。如果 VIP 是永久的，则显示 “forever”。如果已过期，则显示“已过期”。

•
%vip_status%: 显示玩家 VIP 的状态（例如：“永久VIP”、“VIP用户”、“VIP已过期”、“无VIP”）。

配置

config.yml 文件允许您自定义插件的行为和消息。以下是默认配置的示例：

YAML


# VIPShop 插件配置

# VIP 价格设置
vip-prices:
  coin:
    week: 50000
    month: 500000
    permanent: 5000000
  points:
    week: 50
    month: 500
    permanent: 5000

# LuckPerms 组设置
vip-group: vip1 # 购买 VIP 后玩家加入的 LuckPerms 组
default-group: default # VIP 到期后玩家返回的 LuckPerms 组

# 数据存储文件
storage-file: data.yml

# 消息设置
messages:
  prefix: "&b[VIPShop] &r" # 插件消息前缀
  no-permission: "&c你没有权限执行此命令！"
  gui-title: "&6VIP 商店"
  coin-menu-title: "&a金币购买 VIP"
  points-menu-title: "&d点券购买 VIP"
  purchase-success: "&a恭喜你成功购买 VIP！到期时间：{expiry}"
  purchase-failed: "&c购买失败，请稍后再试。"
  insufficient-coins: "&c金币不足！你需要 {amount} 金币。"
  insufficient-points: "&c点券不足！你需要 {amount} 点券。"
  vip-expired: "&c你的 VIP 权限已过期。"
  already-vip: "&e你已经是 VIP 用户了！"
  # GUI 物品名称和描述
  item-names:
    coin-purchase: "&a金币购买 VIP"
    points-purchase: "&d点券购买 VIP"
    week-option: "&e购买一周 VIP"
    month-option: "&e购买一月 VIP"
    permanent-option: "&e购买永久 VIP"
  item-lores:
    coin-purchase:
      - "&7点击使用金币购买 VIP 权限。"
    points-purchase:
      - "&7点击使用点券购买 VIP 权限。"
    week-option:
      - "&7价格：{price} {currency}"
      - "&7点击购买一周 VIP。"
    month-option:
      - "&7价格：{price} {currency}"
      - "&7点击购买一月 VIP。"
    permanent-option:
      - "&7价格：{price} {currency}"
      - "&7点击购买永久 VIP。"


从源代码构建

如果您想从源代码构建此插件，请按照以下步骤操作：

1.
克隆仓库：

2.
安装 Maven： 如果您尚未安装 Maven，请根据您的操作系统进行安装。例如，在 Ubuntu 上：

3.
手动添加 PlayerPoints JAR： 由于 PlayerPoints 的 Maven 仓库不稳定，您需要手动下载 PlayerPoints-3.3.2.jar 并将其放置在 VIPShop/lib/ 目录下。您可以从 Modrinth PlayerPoints 页面 下载。

4.
构建项目：

贡献

欢迎贡献！如果您有任何功能建议、错误报告或改进，请随时提交 Pull Request 或在 GitHub 上开具 Issue。

许可证

本项目采用 MIT 许可证 发布。

