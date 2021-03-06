/*!
 * =====================================================
 * TS v1.0.0 ()
 * =====================================================
 */
! function(a, b) {
	b.BasePage = a.Class.extend({
		navActive: "home",
		navData: b.data.nav,
		init: function() {
			this.initConfig(), this.initPage(), this.initEvent()
		},
		initConfig: function() {},
		initPage: function() {
			this.initNav()
		},
		initEvent: function() {},
		initNav: function() {
			var c = this.navData;
			if (b.IndexPage && !a.os.plus)
				for (var d = 0, e = c.length; e > d; d++) {
					var f = c[d];
					"home" === f.id ? f.url = "index.html" : f.url = "page/" + f.id + "/" + f.id + ".html"
				}
			b.template("nav", "index/nav", {
				navData: c,
				index: this.navActive
			})
		}
	})
}(mui, TS),
function(a, b) {
	b.IndexPage = b.BasePage.extend({
		supplierPath: "page/supplier/supplier.html",
		activityPath: "page/activity/activity.html",
		address_cache_key: "INDEX_ADDRESS",
		initConfig: function() {
			this._super(), this.current = 0, this.size = 100;
			var a = this;
			template.helper("banner", function(b) {
				return a.banner(b)
			})
		},
		initPage: function() {
			this._super();
			var a = this;
			b.showWaiting(), a.initLocation(function() {
				a.initBanners(), a.initItem(), a.initEvent(), b.closeWaiting()
			})
		},
		initEvent: function() {},
		banner: function(a) {
			var b = a.banner_name,
				c = a.banner_url,
				d = (a.item_data, "");
			switch (a.banner_type) {
				case 1:
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					d = "../activity/activity_main.html";
					break;
				case 5:
					break;
				case 6:
					break;
				case 7:
					break;
				case 8:
					break;
				case 9:
					break;
				case 10:
					c = this.activityPath
			}
			return ' data-href="' + c + '" data-url="' + d + '" data-title="' + b + '"'
		},
		initLocation: function(a) {
			var c = this,
				d = b.Site.get_address();
			if (d || (d = b.getItem(c.address_cache_key)), d) {
				b.debug && b.d("address from cache:" + d);
				var e = document.getElementById("ts_title");
				e && (e.innerText = d), b.setItem(c.address_cache_key, d), a && a(d), b.Location.getLocation(function(a) {
					a && (b.debug && b.d("relocate:" + d), b.setItem(c.address_cache_key, a.address))
				})
			} else b.Location.initCity(), setTimeout(function() {
				b.Location.getLocation(function(e) {
					e ? (d = e.address, document.getElementById("ts_title").innerText = d) : (e = b.Location.get_location(), d = e && e.address || b.Location.get_city_name(), document.getElementById("ts_title").innerText = d), b.setItem(c.address_cache_key, d), a && a(d)
				})
			}, 100)
		},
		initBanners: function(a) {
			var c = this;
			b.Api.get_banners(b.Location.get_city_id(), function(a) {
				return a.return_code ? void b.error(a.data) : void c.renderBanners(a)
			})
		},
		renderBanners: function(c) {
			var d = a.filter.call(c.data, function(a) {
				return 1 == a.show_type
			});
			b.template("banners", "index/banners", {
				bannersData: d
			}), document.getElementById("banners").classList.add("mui-active")
		},
		initItem: function(a) {
			this.initPullRefresh()
		},
		getItem: function(a) {
			var c = this;
			b.Api.get_station_item({
				station_id: b.Location.get_station_id(),
				current: c.current,
				size: c.size
			}, function(c) {
				return c.return_code ? void b.error(c.data) : void(a && a(c))
			})
		},
		renderItem: function(c, d) {
			b.template("item", "index/item", {
				itemData: c.data,
				supplierPath: this.supplierPath
			}, d);
			var e = document.getElementById("banners"),
				f = document.getElementById("item");
			if (e.classList.contains("mui-active")) f.classList.contains("mui-active") || (f.classList.add("mui-active"), b.closeWaiting(), a("#banners").slider({
				interval: a.os.ios ? 5e3 : 0
			}));
			else var g = 0,
				h = setInterval(function() {
					(e.classList.contains("mui-active") || g > 10) && (window.clearInterval(h), f.classList.contains("mui-active") || (f.classList.add("mui-active"), b.closeWaiting(), a("#banners").slider({
						interval: a.os.ios ? 5e3 : 0
					}))), g++, b.debug && b.d("interval " + g)
				}, 50)
		},
		initPullRefresh: function() {
			var b, c = this;
			a.init({
				pullRefresh: {
					container: "#pullrefresh",
					down: {
						auto: !0,
						autoY: 0,
						callback: function() {
							c.current = 0, c.getItem(function(d) {
								c.renderItem(d), !b && (b = a("#pullrefresh").pullRefresh()), b.endPulldownToRefresh(), c.current++
							})
						}
					}
				}
			})
		}
	})
}(mui, TS),
function(a, b) {
	a.os.plus && (b.IndexPage = b.IndexPage.extend(a.extend(b.AppObject, {
		supplierPath: "../supplier/supplier.html",
		activityPath: "../activity/activity.html",
		initPage: function() {
			a.plusReady(function() {
				if (plus.runtime.appid === plus.webview.currentWebview().id) {
					plus.screen.lockOrientation("portrait-primary");
					var c = this;
					a.plusReady(function() {
						b.showWaiting(), c.initLogin(), c.initNav(), c.initBack(), c.initTabWebview(), c.initGuide(function(d) {
							plus.storage.removeItem("LOCATION"), c.initLocation(function(c) {
								plus.storage.setItem("LOCATION", "true"), b.debug && b.d("initLocation fire initSubWebview::::nav_home"), a.fire(plus.webview.getWebviewById("nav_home"), "initSubWebview")
							}), plus.navigator.closeSplashscreen()
						})
					})
				} else this.initEvent(), this.initSupplierWebview()
			}.bind(this))
		},
		oAuthLoginCallback: function(a, b) {
			a ? this.oAuthLoginSuccess(a.data, b) : this.oAuthLoginError()
		},
		oAuthLoginSuccess: function(c, d) {
			b.User.init(c), b.User.initUserinfo(d), a.each(plus.webview.all(), function(c, d) {
				b.debug && b.d(d.id), a.fire(d, "user_init")
			})
		},
		oAuthLoginError: function() {},
		initLogin: function() {
			if (a.os.stream && a.os.qihoo) {
				var c = this;
				b.Login = new b.LoginClass(function() {
					b.Login.loginByQihoo(c.oAuthLoginCallback.bind(c))
				})
			}
		},
		initGuide: function(a) {
			plus.storage.getItem("GUIDE");
			a && a(!1)
		},
		initSupplierWebview: function() {
			var c = this;
			a.plusReady(function() {
				c.supplierWebview = mui.preload({
					id: "supplier",
					url: c.supplierPath,
					styles: {
						popGesture: "hide"
					}
				}), c.supplierWebview.addEventListener("loaded", function() {
					b.captureSnapshot(c.supplierWebview, "loading")
				})
			})
		},
		initBack: function() {
			var c = null;
			a.back = function() {
				c ? (new Date).getTime() - c < 1e3 && plus.runtime.quit() : (c = (new Date).getTime(), b.toast("再按一次退出应用"), setTimeout(function() {
					c = null
				}, 1e3))
			}
		},
		initTabWebview: function() {
			var c = plus.webview.currentWebview(),
				d = null;
			a.each(document.querySelectorAll("#nav a"), function(e, f) {
				var g = f.getAttribute("data-href"),
					h = f.id;
				if (g && h) {
					var i = {
						top: "0px",
						bottom: "50px",
						render: "always"
					};
					0 === e && (i.top = b.isStatusbarOffset() ? "64px" : "44px");
					var j = plus.webview.create(g, h, i);
					"nav_order" === h && j.append(plus.webview.create("page/order/order.html", "order", {
						top: b.isStatusbarOffset() ? "108px" : "88px",
						bottom: "0",
						render: "always"
					})), j.hide(), "nav_order" !== h && c.append(j), 0 === e && (d = j, b.debug && b.d("childWebview::::::::" + j.id + "," + d.id), d.addEventListener("loaded", function() {
						d.show(), b.debug && b.d("nav_home loaded"), b.debug && b.d("loaded fire initSubWebview:::" + d.id), a.fire(d, "initSubWebview")
					}))
				}
			});
			var e = document.querySelector("#nav a").id,
				f = document.querySelector("header"),
				g = document.getElementById("pullrefresh");
			return a("#nav").on("touchend", "a", function(b) {
				b.preventDefault();
				var c = this.getAttribute("data-href"),
					d = this.id;
				if (c && e !== d) {
					var h = plus.webview.getWebviewById(d);
					"nav_home" !== d && (f.classList.add("mui-hidden"), g.classList.add("mui-hidden"), f.offsetHeight, g.offsetHeight), h.show("fade-in", 200, function() {
						"nav_home" !== d && (f.classList.remove("mui-hidden"), g.classList.remove("mui-hidden"), f.offsetHeight, g.offsetHeight)
					}), a.fire(h, d, {
						force: !1
					}), plus.webview.hide(e), e = d
				}
				var i = document.querySelector("#nav a.mui-active");
				return i !== this && (i.classList.remove("mui-active"), this.classList.add("mui-active")), !1
			}), d
		},
		renderBanners: function(c) {
			var d = document.getElementById("pullrefresh");
			a.each(c.data, function(c, e) {
				var f = e.banner_image;
				b.Image.get(e.banner_image, function(b) {
					b || (b = f), a.each(a.qsa(".banner_" + e.banner_id, d), function(a, c) {
						c.src = b
					})
				}), e.banner_image = b.placeholder.banner
			}), this._super(c)
		},
		renderItem: function(c, d) {
			a.each(c.data, function(a, c) {
				var d = c.item_mainimage;
				b.Image.get(c.item_mainimage, function(a) {
					a || (a = d);
					var b = document.getElementById("item_" + c.item_id);
					b ? b.src = a : setTimeout(function() {
						b.src = a
					}, 500)
				}), c.item_mainimage = b.placeholder.item
			}), this._super(c, d)
		},
		init5plusEvent: function() {
			var c = this;
			a.plusReady(function() {
				plus.runtime.appid !== plus.webview.currentWebview().id || document.addEventListener("goHome", function() {
					var b = document.getElementById("nav_home");
					a.trigger(b, "touchstart"), a.trigger(b, "tap")
				})
			});
			var d = !1;
			document.addEventListener("initSubWebview", function() {
				if (b.debug && b.d("receive initSubWebview"), d) return void(b.debug && b.d("已完成初始化"));
				var a = plus.storage.getItem("LOCATION");
				a ? (d = !0, b.debug && b.d("已完成定位"), c.initBanners(), c.initItem()) : b.debug && b.d("尚未定位完成")
			}), document.addEventListener("address", function() {
				plus.runtime.appid === plus.webview.currentWebview().id ? document.getElementById("ts_title").innerText = b.Site.get_address() : setTimeout(function() {
					a("#pullrefresh").pullRefresh().pulldownLoading()
				}, 500)
			})
		}
	})))
}(mui, TS);