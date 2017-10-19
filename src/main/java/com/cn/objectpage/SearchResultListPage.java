package com.cn.objectpage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.cn.basepage.BasePage;

public class SearchResultListPage extends BasePage {
	/*
	 * 本页面是搜索结果列表页，通过首页输入关键字，点击搜索，跳转过来
	 */

	// 元素定位
	// 第一页第一个商品
	@FindBy(xpath = "//*[@id='J_goodsList']/ul/li[1]/div/div[1]/a/img")
	WebElement searchResult_firstItem_img;

	// 第一页第2个商品,为了和第一个对比
	@FindBy(xpath = "//*[@id='J_goodsList']/ul/li[2]/div/div[1]/a/img")
	WebElement searchResult_secondItem_img;

	// 第一个商品的价格
	@FindBy(xpath = "//*[@id='J_goodsList']/ul/li[1]/div/div[3]/strong/i")
	WebElement searchResult_firstItem_price;

	public SearchResultListPage(WebDriver driver) {
		super(driver);

	}

	/*
	 * 点击商品图片，进入商品详情页
	 */
	public GoodsDetailsPage clickItemImg() throws InterruptedException {

		click(searchResult_firstItem_img);
		Thread.sleep(1000);
		return new GoodsDetailsPage(driver);
	}

	/*
	 * 获取商品价格，好和详情页价格对比
	 */
	public String getGoodsPriceOnListPage() {

		return searchResult_firstItem_price.getText();

	}

}
