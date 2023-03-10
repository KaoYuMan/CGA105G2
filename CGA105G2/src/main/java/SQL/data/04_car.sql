-- 2-5-2.購物車
CREATE TABLE `cga105g2`.`CART`
(
    `MEM_ID`   INT NOT NULL COMMENT '會員編號',
    `GOODS_ID` INT NOT NULL COMMENT '商品編號',
    `CART_NUM` INT NOT NULL COMMENT '商品數量',
    PRIMARY KEY (`MEM_ID`,`GOODS_ID`),
    CONSTRAINT `FK_CART_MEMBER_MEM_ID`       FOREIGN KEY (`MEM_ID`)      REFERENCES `MEMBER` (`MEM_ID`),
    CONSTRAINT `FK_CART_GOODS_GOODS_ID`       FOREIGN KEY (`GOODS_ID`)      REFERENCES `GOODS` (`GOODS_ID`)
);