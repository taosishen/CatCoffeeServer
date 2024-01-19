/*
Navicat MySQL Data Transfer

Source Server         : emall
Source Server Version : 50726
Source Host           : localhost:3306
Source Database       : catcoffee

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2024-01-18 21:03:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for classification
-- ----------------------------
DROP TABLE IF EXISTS `classification`;
CREATE TABLE `classification` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '分类名称',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '顺序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_category_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1747468271514243074 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='菜品及套餐分类';

-- ----------------------------
-- Records of classification
-- ----------------------------
INSERT INTO `classification` VALUES ('1747229738618773506', '超值套餐', '1', '2024-01-16 20:10:06', '2024-01-16 20:10:06');
INSERT INTO `classification` VALUES ('1747231923662413825', '爱宠', '2', '2024-01-16 20:18:47', '2024-01-16 20:18:47');
INSERT INTO `classification` VALUES ('1747232496189104129', '特色美食', '3', '2024-01-16 20:21:04', '2024-01-16 20:21:04');
INSERT INTO `classification` VALUES ('1747233037120102401', '饮品系列', '0', '2024-01-16 20:23:13', '2024-01-16 20:23:13');
INSERT INTO `classification` VALUES ('1747234725230993410', '创意饮品', '5', '2024-01-16 20:29:55', '2024-01-16 20:29:55');
INSERT INTO `classification` VALUES ('1747236380290768898', '刨冰', '7', '2024-01-16 20:36:30', '2024-01-16 20:36:30');
INSERT INTO `classification` VALUES ('1747237739098464258', '尽享美食', '9', '2024-01-16 20:41:54', '2024-01-16 20:41:54');
INSERT INTO `classification` VALUES ('1747468271514243073', 'test1', '3', '2024-01-17 11:57:57', '2024-01-17 11:57:57');

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '菜品名称',
  `category_id` bigint(20) NOT NULL COMMENT '菜品分类id',
  `price` decimal(10,2) NOT NULL COMMENT '菜品价格',
  `code` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '商品码',
  `image` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '图片',
  `description` varchar(400) COLLATE utf8_bin DEFAULT NULL COMMENT '描述信息',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '0 停售 1 起售',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '顺序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_goods_name` (`name`) USING BTREE,
  KEY `out_goods_name` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1747467965804007427 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商品信息';

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES ('1747230924252045314', '双人撸猫+红枣桂圆红茶', '1747229738618773506', '8888.00', '001', '4393e7e0-82ca-4c92-87ad-ac23c1e31779.jpg', null, '1', '0', '2024-01-16 20:14:49', '2024-01-16 20:14:49');
INSERT INTO `goods` VALUES ('1747231469419290626', '四人撸猫+双份咖啡+双份果饮', '1747229738618773506', '20000.00', '002', '4ee10f11-1b09-417c-8a25-e1057df684fc.jpg', null, '1', '0', '2024-01-16 20:16:59', '2024-01-16 20:16:59');
INSERT INTO `goods` VALUES ('1747231763406446593', '单人撸猫+飘香奶茶', '1747229738618773506', '3666.00', '003', 'fa327c08-7748-45d5-ae4f-161c09825720.jpg', null, '1', '0', '2024-01-16 20:18:09', '2024-01-16 20:18:09');
INSERT INTO `goods` VALUES ('1747232290441715713', '猫条', '1747231923662413825', '1000.00', '004', 'd52860f8-5e00-4956-a7e0-8474000930fb.jpg', '猫咪最爱的食物', '1', '0', '2024-01-16 20:20:15', '2024-01-18 16:15:09');
INSERT INTO `goods` VALUES ('1747232704100753409', '鸡翅', '1747232496189104129', '1800.00', '005', 'ca5c2ea9-ed53-4e8b-9d68-583a6cdfae68.jpg', null, '1', '0', '2024-01-16 20:21:53', '2024-01-16 20:21:53');
INSERT INTO `goods` VALUES ('1747232907050541057', '意面', '1747232496189104129', '2990.00', '006', 'ad369970-ff54-4d61-999d-5dc21d838f28.jpg', null, '1', '0', '2024-01-16 20:22:42', '2024-01-16 20:22:42');
INSERT INTO `goods` VALUES ('1747233614990336001', '青椰美式', '1747233037120102401', '2200.00', '006', 'db9fdf18-8a4b-48ad-9163-763f363f9307.jpg', null, '1', '0', '2024-01-16 20:25:31', '2024-01-16 20:25:31');
INSERT INTO `goods` VALUES ('1747233847774208002', '荔枝美式', '1747233037120102401', '2100.00', '007', '4932ce7e-e10e-4539-967c-2e83dc343f62.jpg', null, '1', '0', '2024-01-16 20:26:26', '2024-01-16 20:26:26');
INSERT INTO `goods` VALUES ('1747233997540220930', '浓缩美式', '1747233037120102401', '1800.00', '007', '1590b9fe-3cbf-46a0-99ff-de660e3bc4e0.jpg', null, '1', '0', '2024-01-16 20:27:02', '2024-01-16 20:27:02');
INSERT INTO `goods` VALUES ('1747234158861541378', '生椰拿铁', '1747233037120102401', '2800.00', '110', 'ac38864b-962d-41ee-9695-1108221bd25f.jpg', null, '1', '0', '2024-01-16 20:27:40', '2024-01-16 20:27:40');
INSERT INTO `goods` VALUES ('1747234384791920641', '樱花拿铁', '1747233037120102401', '2800.00', '006', '218b9aa4-4da0-437b-9427-ab2b0e163a20.jpg', null, '1', '0', '2024-01-16 20:28:34', '2024-01-16 20:28:34');
INSERT INTO `goods` VALUES ('1747234579248242689', '薄荷拿铁', '1747233037120102401', '2800.00', '008', '3fc63903-972c-4801-95c9-10a038a34711.jpg', null, '1', '0', '2024-01-16 20:29:20', '2024-01-16 20:29:20');
INSERT INTO `goods` VALUES ('1747235458672156674', '香草冰淇淋拿铁', '1747234725230993410', '3000.00', '010', 'd3a8600d-9e22-4814-b06c-27c9720474d9.jpg', null, '1', '0', '2024-01-16 20:32:50', '2024-01-16 20:32:50');
INSERT INTO `goods` VALUES ('1747235658283278338', '草莓苦瓜拿铁', '1747234725230993410', '3000.00', '010', '909070f1-404d-40f9-9f5e-a979770d698c.jpg', null, '1', '0', '2024-01-16 20:33:38', '2024-01-16 20:33:38');
INSERT INTO `goods` VALUES ('1747235791804751873', '草莓大福拿铁', '1747234725230993410', '3000.00', '010', '486ee696-70a7-4f69-83b1-ccba271a46ce.jpg', null, '0', '0', '2024-01-16 20:34:09', '2024-01-16 20:34:09');
INSERT INTO `goods` VALUES ('1747236091777179650', '焦糖玛奇朵', '1747234725230993410', '3000.00', '1001', '8f58a1b4-faf7-4897-a696-c5db29dd22a3.jpg', null, '1', '0', '2024-01-16 20:35:21', '2024-01-16 20:35:21');
INSERT INTO `goods` VALUES ('1747236835918012417', '杨枝甘露绵绵冰', '1747236380290768898', '3800.00', '010', '25c6b6aa-9ebf-4c2b-8201-bf09eb60d672.jpg', null, '1', '0', '2024-01-16 20:38:18', '2024-01-16 20:38:18');
INSERT INTO `goods` VALUES ('1747237270284328961', '牛奶绵绵冰', '1747236380290768898', '3800.00', '011', 'b60a4214-3e3a-48e4-bc6f-058736177132.jpg', null, '1', '0', '2024-01-16 20:40:02', '2024-01-16 20:40:02');
INSERT INTO `goods` VALUES ('1747237455873892353', '草莓绵绵冰', '1747236380290768898', '3800.00', '112', 'e38629ee-522a-46ae-8ceb-fb32b84ab216.jpg', null, '1', '0', '2024-01-16 20:40:46', '2024-01-16 20:40:46');
INSERT INTO `goods` VALUES ('1747237948637503489', '薯条', '1747237739098464258', '1490.00', '112', '49ba420b-0946-4afe-be36-978463861018.jpg', null, '1', '0', '2024-01-16 20:42:44', '2024-01-16 20:42:44');
INSERT INTO `goods` VALUES ('1747238110948679681', '提拉米苏', '1747237739098464258', '2000.00', '445', '1fc21b34-c57c-4146-b4b7-76257d1f0831.jpg', null, '1', '0', '2024-01-16 20:43:22', '2024-01-16 20:43:22');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `number` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '订单号',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '订单状态 1已付款，2已完成，',
  `user_id` bigint(20) NOT NULL COMMENT '下单用户',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `amount` decimal(10,2) NOT NULL COMMENT '实收金额',
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `remark` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `phone` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `num` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='订单表';

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES ('36', 'e9afb060-42a1-4c03-bf9c-b1e8641e1366', '2', '1732585320288784386', '2024-01-16 21:22:23', '10.00', '111', '', '13790602334', '1');
INSERT INTO `orders` VALUES ('37', 'd3318cef-3fd9-4f39-9b13-fae7ff9f6b1b', '2', '1732585320288784386', '2024-01-16 21:22:36', '30.00', '111', '', '13790602334', '1');
INSERT INTO `orders` VALUES ('38', '14cae9a8-46f0-4421-b37c-4e3595523abb', '1', '1732585320288784386', '2024-01-17 11:55:39', '60.00', '111', 'effff', '13790602334', '2');
INSERT INTO `orders` VALUES ('39', '2c430bf0-40a4-423e-9f49-4f7fe7210678', '1', '1732585320288784386', '2024-01-18 15:15:49', '89.90', '111', '吴大大哇的地位的', '13790602334', '3');

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '名字',
  `image` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '图片',
  `order_id` bigint(50) NOT NULL COMMENT '订单id',
  `dish_id` bigint(20) DEFAULT NULL COMMENT '菜品id',
  `setmeal_id` bigint(20) DEFAULT NULL COMMENT '套餐id',
  `dish_flavor` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '口味',
  `number` int(11) NOT NULL DEFAULT '1' COMMENT '数量',
  `amount` decimal(10,2) NOT NULL COMMENT '金额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1747880453376782339 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='订单明细表';

-- ----------------------------
-- Records of order_detail
-- ----------------------------
INSERT INTO `order_detail` VALUES ('1747247926978957314', '猫条', 'd52860f8-5e00-4956-a7e0-8474000930fb.jpg', '36', '1747232290441715713', '1747231923662413825', null, '1', '1000.00');
INSERT INTO `order_detail` VALUES ('1747247983308460033', '香草冰淇淋拿铁', 'd3a8600d-9e22-4814-b06c-27c9720474d9.jpg', '37', '1747235458672156674', '1747234725230993410', null, '1', '3000.00');
INSERT INTO `order_detail` VALUES ('1747467691890790402', '香草冰淇淋拿铁', 'd3a8600d-9e22-4814-b06c-27c9720474d9.jpg', '38', '1747235458672156674', '1747234725230993410', null, '1', '3000.00');
INSERT INTO `order_detail` VALUES ('1747467691899179010', '草莓苦瓜拿铁', '909070f1-404d-40f9-9f5e-a979770d698c.jpg', '38', '1747235658283278338', '1747234725230993410', null, '1', '3000.00');
INSERT INTO `order_detail` VALUES ('1747880453368393730', '草莓大福拿铁', '486ee696-70a7-4f69-83b1-ccba271a46ce.jpg', '39', '1747235791804751873', '1747234725230993410', null, '1', '3000.00');
INSERT INTO `order_detail` VALUES ('1747880453368393731', '焦糖玛奇朵', '8f58a1b4-faf7-4897-a696-c5db29dd22a3.jpg', '39', '1747236091777179650', '1747234725230993410', null, '1', '3000.00');
INSERT INTO `order_detail` VALUES ('1747880453376782338', '意面', 'ad369970-ff54-4d61-999d-5dc21d838f28.jpg', '39', '1747232907050541057', '1747232496189104129', null, '1', '2990.00');

-- ----------------------------
-- Table structure for shopping_cart
-- ----------------------------
DROP TABLE IF EXISTS `shopping_cart`;
CREATE TABLE `shopping_cart` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `image` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '图片',
  `user_id` bigint(20) NOT NULL COMMENT '主键',
  `good_id` bigint(20) NOT NULL COMMENT '产品id',
  `class_id` bigint(20) DEFAULT NULL COMMENT '分类id',
  `number` int(11) NOT NULL DEFAULT '1' COMMENT '数量',
  `amount` decimal(10,2) NOT NULL COMMENT '金额',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='购物车';

-- ----------------------------
-- Records of shopping_cart
-- ----------------------------
INSERT INTO `shopping_cart` VALUES ('1747895780760301570', '猫条', 'd52860f8-5e00-4956-a7e0-8474000930fb.jpg', '1732585320288784386', '1747232290441715713', null, '1', '1000.00', '2024-01-18 16:16:43');

-- ----------------------------
-- Table structure for staff
-- ----------------------------
DROP TABLE IF EXISTS `staff`;
CREATE TABLE `staff` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '姓名',
  `username` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `password` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '密码',
  `phone` varchar(11) COLLATE utf8_bin NOT NULL COMMENT '手机号',
  `sex` varchar(2) COLLATE utf8_bin NOT NULL COMMENT '性别',
  `id_number` varchar(18) COLLATE utf8_bin NOT NULL COMMENT '身份证号',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态 0:禁用，1:启用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=1747226014517223427 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='员工信息';

-- ----------------------------
-- Records of staff
-- ----------------------------
INSERT INTO `staff` VALUES ('1729491280982884353', '管理员', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '13812312312', '1', '110101199001010047', '1', '2022-02-15 15:51:20', '2022-02-17 09:16:20');
INSERT INTO `staff` VALUES ('1730958650486173697', '陈晓阳', 'chenxiaoyang', '09e93b45ea44aed9db5b6073b0c30ae3', '13725632356', '1', '441855555566662322', '1', '2023-12-02 22:34:37', '2023-12-05 18:14:39');
INSERT INTO `staff` VALUES ('1731979626976018434', '789456', 'test111', 'e10adc3949ba59abbe56e057f20f883e', '13790602222', '0', '445966622233335685', '1', '2023-12-05 18:11:36', '2024-01-16 19:33:02');
INSERT INTO `staff` VALUES ('1747226014517223426', 'xxxx', '79', '9fab6755cd2e8817d3e73b0978ca54a6', '13723332333', '0', '44444444444444444', '1', '2024-01-16 19:55:18', '2024-01-16 19:55:18');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '昵称',
  `username` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(64) COLLATE utf8_bin NOT NULL,
  `phone` varchar(11) COLLATE utf8_bin NOT NULL COMMENT '手机号',
  `sex` varchar(2) COLLATE utf8_bin DEFAULT NULL COMMENT '性别',
  `avatar` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '头像',
  `money` decimal(20,2) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '注册时间',
  PRIMARY KEY (`id`,`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=1745263069172207618 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户信息';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1729687434483900418', 'GGbond', '@8cda08c2-ea20-4f07-bbac-2404b957eb05', 'e10adc3949ba59abbe56e057f20f883e', '15918625958', '1', 'man.png', '11.00', '2024-01-02 21:02:25');
INSERT INTO `user` VALUES ('1729788460515897345', 'GGbond', '@d3730b36-5fb3-4f6d-84fa-d0b7e20c1114', 'e10adc3949ba59abbe56e057f20f883e', '15918655555', '0', '182e40b8-e45f-467d-8994-25525633f761.jpg', '11.00', '2024-01-02 21:02:22');
INSERT INTO `user` VALUES ('1729788554988400642', 'GGbond', '@a38eb512-d3fe-426b-80c1-4bcd0ed9601a', 'e10adc3949ba59abbe56e057f20f883e', '1591866666', '1', 'woman.png', '11.00', '2024-01-05 21:02:17');
INSERT INTO `user` VALUES ('1732585320288784386', '111', '@ff117edc-c686-4cab-ba37-75b2b66e943c', 'e10adc3949ba59abbe56e057f20f883e', '13790602334', '1', 'man.png', '1627.70', '2024-01-01 21:02:37');
INSERT INTO `user` VALUES ('1739949954058567682', 'csc', '@bda6b1af-6c67-4655-b161-88ce545ce460', '123456', '15366662953', '2', 'none.png', '1111.00', '2024-01-01 21:02:41');
INSERT INTO `user` VALUES ('1743481826189422593', 'sadasd', '@5505eaa2-c051-4b79-b631-a85479216eb7', '123456', '13755556662', '2', 'none.png', null, '2024-01-06 11:57:14');
