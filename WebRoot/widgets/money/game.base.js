$(function() {
	//提交用户数据时验证方法
    function p(a) {
        var b = !0;
        $("#" + a).find("input[required]").each(function() {
            var a = $(this);
            if ("" == a.val()) return alert(a.attr("placeholder") + "不能为空！"),
            b = !1;
            if ("J_Mobile" == a.attr("id") && !/^\d{11}$/.test(a.val())) return alert("请输入正确的手机号！"),
            b = !1
        });
        var carSeriy=$('#carSeriy').val();
        if(null==carSeriy||carSeriy==''){
        	alert("请选择车系！");
        	return false;
        }
        var carModel=$('#carModel').val();
        if(null==carModel||carModel==''){
        	alert("请选择车型！");
        	return false;
        }
        return b
    }
    //提交用户数据方法
    function s(a) {
        if (!e) {
            e = !0;
            //var b = $("#regFrame").find("input[class*='frame-input']");
            var b=$("#frmId").serialize();
            $.post(APP.URL_USER, b,
            function(b) {
            	if(b.success){
            		APP.uid = b.uid;
            		a && a(), e = !1
            	}else{
            		alert(b.error)
            	}
                //b.success ? (APP.uid = b.uid | 0, a && a(), e = !1) : alert(b.error)
            },
            "json")
        }
    }
    //游戏重置
    function f() {
        game.go("popupPage")
    }
    //再玩一次方法
    function q() {
        var a = !1;
        0 < APP.remainAllTimes ? ($("#J_Get").text("再玩一次").attr({
            role: "play"
        }).parent().show(), a = !0) : $("#J_Get").parent().hide();
        return a
    }
    function u() {
        if (!k) {
            k = !0;
            var a = game.getSum() || 0,
            a = 0 < APP.MAX_SUM && a > APP.MAX_SUM ? APP.MAX_SUM: a;
            $.post(APP.URL_GET_TICKET, {
                uid: APP.uid,
                score: a
            },
            function(a) {
                a.success ? (game.end(!1), game.showTicket(), k = !1) : alert(a.error)
            },
            "json")
        }
    }
    function r() {
        var a = stage.getChildByName("ticketPage"),
        b = stage.getChildByName("rankPage"),
        d = game.getSum() || 0;
        a.isVisible() ? 0 < a.sum ? (shareData.title = "抢钱啦！我参加疯狂划算抢到了" + a.sum + "元！", shareData.desc = shareData.titleDefault) : (shareData.title = shareData.titleDefault, shareData.desc = shareData.descDefault) : b.isVisible() ? (shareData.title = 0 < APP.uMax ? APP.IIMEOUT + "秒就抢到了" + APP.uMax + "元，轻轻松松挤进富豪榜第" + APP.uRank + "位，你行吗？": "富豪榜的首富竟然不是我，要不你来试试？", shareData.desc = shareData.descDefault) : game.isIng() ? (shareData.title = "疯狂赚钱ing，赶紧加入我们！", shareData.desc = "我在" + APP.customer + "参加了疯狂划算活动，心动不如行动，赶紧加入我们！") : 0 < d ? (shareData.title = APP.IIMEOUT + "秒就抢到了" + d + "元，你能超过我吗？", shareData.desc = "作为宇宙无敌超级黄金点钞手，郑重向你发出挑战，敢不敢和我一较高下！") : (shareData.title = shareData.titleDefault, shareData.desc = shareData.descDefault)
    }
    var t, e = !1,
    k = !1,
    l = !1,
    m = !1,
    n = !1;
    $.ajaxSettings.timeout = 3E4;
    $("#J_GetVerCode").click(function() {
        var a = $(this),
        b = $("#J_Mobile").val(),
        d = 60;
        if (!/^\d{11}$/.test(b)) return alert("请输入正确的手机号！"),
        !1;
        if (a.hasClass("ver-btn-wait")) return ! 1;
        a.addClass("ver-btn-wait");
        t = setInterval(function() {
            d ? (a.text(d + "秒后重发"), d--) : (clearInterval(t), a.removeClass("ver-btn-wait").text("获取验证码"))
        },
        1E3);
        $.post(APP.URL_VERIFY, {
            mobile: b
        },
        function(a) {
            a.success || alert(a.error)
        },
        "json")
    });
    $("#J_RegClose").click(function() {
        f()
    });
    $("#J_Go").click(function() {
        p("regFrame") && s(f)
    });
    $("#J_CancelScore").click(function() {
        f();
        APP.getScore()
    });
    $("#J_SaveScore").click(function() {
        p("regFrame") && s(APP.getScore)
    });
    APP.timestamp = "";
    APP.getScore = function(a, b) {
        var d = game.getSum() || 0,
        c = 3,
        g = !0,
        h = "";
        a = a || ( + new Date + "").substr(3);
        $("#J_UserSum").text("￥" + d);
        $.post(APP.URL_SCORE, {
            uid: APP.uid,
            score: d,
            _t: a,
            _scoreFirst: !!b
        },
        function(a) {
            if (a.success) {
                h = "<p>人类已经无法阻止你成为土豪了，</p><p>赶紧【领取】礼品兑换券，去大金库查看吧！</p>";
                0 < a.max_sum && d > a.max_sum ? ($("#J_UserSum").text("￥" + a.max_sum), h = "哇塞，也太快了吧！你的点钞速度已经打破了最快记录，您最多可以领取" + a.max_sum + "元哦，赶紧【领取】礼品券，去大金库兑换吧！") : $("#J_UserSum").text("￥" + d);
                APP.remainAllTimes = a.remainAllTimes;
                APP.remainDayTimes = a.remainDayTimes;
                APP.IIMEOUT = a.timeout;
                APP.customer = a.customer;
                APP.end = a.end;
                APP.MAX_SUM = a.max_sum;
                g = game.checkPlayable();
                if (!g) {
                    game.end(!1);
                    return
                }
                0 < a.max ? ($("#J_UserMax").text("￥" + a.max), $("#J_UserRank").text(a.rank + "位"), $("#getFrame").find(".get-score").show()) : $("#getFrame").find(".get-score").hide();
                APP.followed ? ($("#J_Follow").parent().hide(), c--) : $("#J_Follow").parent().show();
                //判断是否登记用户信息 是登记记录
                if(a.jionStatus==true){
                	if(a.total_remain>0){
                		if(a.remain>0){
                			$("#J_Get").text("领取").attr({
        	                    role: "get"
        	                }).parent().show();
                		}else{
                			if(a.remainAllTimes>0){
                				h = "<div>您还剩下"+a.remainAllTimes+"次机会,赶紧玩游戏刷榜吧！</div>", q() || c--;
                			}else{
                				h = "<div>亲，好可惜，游戏次数已经用完啦，请关注后续活动吧~</div>", q() || c--;
                			}
                		}
                	}else{
                		if(a.remainAllTimes>0){
                			h = "<div>您还剩下"+a.remainAllTimes+"次机会,赶紧玩游戏刷榜吧！</div>", q() || c--;
                		}else{
            				h = "<div>亲，好可惜，游戏次数已经用完啦，请关注后续活动吧~</div>", q() || c--;
            			}
                	}
	               if(0 >= APP.uid){
	            	  h = "亲，您不需要记录游戏成绩哦~想要领取礼品券，赶紧再玩一次记录成绩吧！";
	               }
                	/*0 < a.total_remain ? 0 < a.remain || -1 == a.remain ? $("#J_Get").text("领取").attr({
	                    role: "get"
	                }).parent().show() : (h = "<div>亲，您的礼品券已经领完了！</div><div>赶紧玩游戏刷榜吧！</div>", q() || c--) : (h = "<div>亲，好可惜！礼品券已经被抢完了！</div><div>赶紧玩游戏刷榜吧！</div>", q() || c--, 0 >= APP.uid && (h = "亲，您不需要记录游戏成绩哦~想要领取礼品券，赶紧再玩一次记录成绩吧！"));*/
	                
	                //判断记录是否达到领奖记录
	                if(0 < a.total_remain){
	                	d < a.min_sum && (h = "您还有"+a.remainAllTimes+"次机会，</div><div>赶紧【再玩一次】，数钱数到手软！</div>", q() || c--);
	                }else{
	                	d < a.min_sum && (h = "<br><div>您还剩下"+a.remainAllTimes+"次机会</div>", q() || c--);
	                }
                }else{
                	 //判断是否登记用户信息 未登记
                	h = "<div>点击【再玩一次】继续试玩，数钱数到手软！</div>", q() || c--;
                }
               
               
                $("#getFrame").find(".get-txt").html(h);
               
                3 == c ? $("#J_BottomBtn").addClass("bottom-btn3") : $("#J_BottomBtn").removeClass("bottom-btn3");
                f();
                game.end(!0)
            } else alert(a.error);
            APP.timestamp = a._t
        },
        "json")
    };
    $("#J_GetClose").click(function() {
        game.end(!1)
    });
    $("#J_Follow").click(function() {
        game.showGuide("follow", "endPage")
    });
    $("#J_Share").click(function() {
        game.showGuide("share", "endPage")
    });
    $("#J_Get").click(function() {
        var a = $(this).attr("role");
        "get" == a ? u() : "play" == a && game.end(!1)
    });
    $("#J_ExchangeNo").click(function() {
        game.go("exchangePage");
        game.go("ticketPage", !0)
    });
    $("#J_ExchangeYes").click(function() {
        if (p("exchangeFrame")) {
            var a = $("#exchangeFrame").attr("data-id"),
            b = $("#J_SN").val();
            $.post(APP.URL_EXCHANGE, {
                uid: APP.uid,
                snid: a,
                sn: b
            },
            function(b) {
                if (b.success) {
                    game.go("exchangePage");
                    var c = $("#J_TicketList").children("[data-id='" + a + "']");
                    c.find(".exchange").hide();
                    c.find(".status").text(b.status);
                    game.go("ticketPage", !0)
                } else alert(b.error)
            },
            "json")
        }
    });
    $("#J_TipYes").click(function() {
        game.go("tooltipPage")
    });
    APP.showTip = function(a) {
        $("#J_TipTitle").text(a)
    };
    APP.loadRule = function(a) {
        n || (n = !0, $.get(APP.URL_RULE, {
            uid: APP.uid
        },
        function(data) {
        	if(data.sucess){
        		if(0 < data.allTimes || 0 < data.dayTimes){
        			if(0 < data.allTimes){
        				$("#J_UsedAllTimes").text(data.usedAllTimes);
        				$("#J_AllTimes").text(data.allTimes).parent().show();
        			}else{
        				 $("#J_AllTimes").text(data.allTimes).parent().hide();
        			}
        			if( 0 < data.dayTimes){
	   					 $("#J_UsedDayTimes").text(data.usedDayTimes), $("#J_DayTimes").text(data.dayTimes).parent().show()
	   				 }else{
	   					 $("#J_DayTimes").text(data.dayTimes).parent().hide();
	   					 $("#J_TimesDesc").show();
	   				 }
        			$("#J_GameExpires").html(data.expires);
        			$("#J_GameRule").html(data.rule);
        			$("#J_ExchangeDesc").html(data.exchange)
        		}
        		else{
        			$("#J_TimesDesc").hide();
        			$("#J_GameExpires").html(data.expires);
        			$("#J_GameRule").html(data.rule);
        			$("#J_ExchangeDesc").html(data.exchange)
        		} 
        	}
        	if(data.error){
        		alert(data.error);
        	}
            //a.success ? (0 < a.allTimes || 0 < a.dayTimes ? (0 < a.allTimes ? ($("#J_UsedAllTimes").text(a.usedAllTimes), $("#J_AllTimes").text(a.allTimes).parent().show()) : $("#J_AllTimes").text(a.allTimes).parent().hide(), 0 < a.dayTimes ? ($("#J_UsedDayTimes").text(a.usedDayTimes), $("#J_DayTimes").text(a.dayTimes).parent().show()) : $("#J_DayTimes").text(a.dayTimes).parent().hide(), $("#J_TimesDesc").show()) : $("#J_TimesDesc").hide(), $("#J_GameExpires").html(a.expires), $("#J_GameRule").html(a.rule), $("#J_ExchangeDesc").html(a.exchange)) : alert(a.error);
            $("#ruleFrame").show();
            n = !1
        },
        "json"))
    };
    $(".ticket-list").delegate("li", "click",
    function(a) {
        a = a.target;
        var b = $(a).attr("seed");
        if (b) switch (b) {
        case "arrow":
            $(this).find(".detail-wrap").toggle();
            $(a).toggleClass("arrow-btn-on");
            break;
        case "exchange":
            a = $(this).attr("data-id");
            game.abort();
            game.go("ticketPage");
            $("#J_SN").val("");
            game.go("exchangePage", !0);
            $("#exchangeFrame").attr("data-id", a);
            break;
        case "use":
            (a = $(a).attr("data-url")) && window.open(a)
        }
    });
    $(".tab-wrap").scroll(function(a) {
        a = $(this).scrollTop() + $(this).height();
        var b = $(this).children(".tab-inner").height();
        b < a || b <= a + 100 && $(".tab-frame[data-role]").each(function() {
            var a;
            if ("hidden" != $(this).css("visibility")) return a = $(this).attr("data-role"),
            a = stage.getChildByName(a),
            APP.loadMore(a),
            !1
        })
    });
    APP.loadMore = function(a, b) {
        if (a) {
            b = void 0 == b ? a.page: b;
            b = 0 < b ? b: 0;
            var d = a.name,
            c, g;
            "ticketPage" == d ? (c = APP.URL_TICKET, g = "ticketFrame", m ? c = "": m = !0) : "rankPage" == d && (c = APP.URL_RANK, g = "rankFrame", l ? c = "": l = !0);
            c && $.get(c, {
                uid: APP.uid,
                pageSize: 18,
                page: ++b
            },
            function(c) {
                c.success ? (a.reload(c), a.page = (c.result || []).length ? b: --b) : alert(c.error);
                "ticketPage" == d ? m = !1 : "rankPage" == d && (l = !1);
                var e = $("#" + g).find(".tab-wrap");
                c = $("#" + g).find(".tab-inner");
                var e = e.height(),
                f = c.height();
               
                e > f && c.css("paddingBottom", e - f + 20)
            },
            "json")
        }
    };
    $(document).on("ajaxError",
    function(a, b, d) {
        404 != b.status && (n = m = l = k = e = !1, alert("网络不给力啊，请重试一下。"), -1 < (d.data || "").indexOf("_scoreFirst=true") && game.end(!1))
    });
    $(document).on("ajaxStart",
    function() {
        $("#J_Loading").show()
    });
    $(document).on("ajaxComplete",
    function() {
        $("#J_Loading").hide()
    });
});