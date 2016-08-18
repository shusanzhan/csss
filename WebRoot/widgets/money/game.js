!
function(a, b) {
    function z(a, b) {
        return 0 == b ? a: z(b, a % b)
    }
    function A(a, b) {
        return 1 == b ? a[0] : z(a[b - 1], A(a, b - 1))
    }
    function B() {
        var d, a = APP.currency || [],
        b = a.length,
        c = [];
        for (d = 0; b > d; d++) c.push( + Math.round(a[d].p)),
        l.push( + a[d].v);
        var e = A(c, b),
        f = !1;
        for (d = 0; b > d; d++) c[d] = c[d] / e,
        c[d] > 9 && (f = !0);
        if (f) for (d = 0; b > d; d++) c[d] = Math.round(c[d] / c[b - 1]),
        0 == c[d] && (c[d] = 1);
        for (d = 0; b > d; d++) {
            for (var g = 0; g < c[d]; g++) k.push(d);
            j += c[d]
        }
    }
    function C(a, b) {
        var c = d.getChildByName("guidePage");
        a && (c.getChildByName("img").image = e.getResult(a), c.visible = !0, b && (d.getChildByName(b).visible = !1, c.fromPage = b))
    }
    function D() {
        var c = new b.Shape;
        c.graphics.beginFill("#488451").rect(0, 0, f, g),
        d.addChild(c),
        E();
        var i = new a.PlayerCurrency;
        d.addChild(i);
        var m = new b.Bitmap(e.getResult("currLogo"));
        m.x = 394,
        m.y = 344,
        m.scaleX = 80 / m.getBounds().width,
        m.scaleY = 80 / m.getBounds().height,
        d.addChild(m);
        var n = m.clone();
        n.name = "cloneLogo",
        n.visible = !1,
        d.addChild(n);
        var o = new a.SumInfo;
        o.name = "sumInfo",
        o.y = 30,
        d.addChild(o);
        var p = new b.Bitmap(e.getResult("starttip"));
        p.name = "arrow",
        p.x = (f - p.getBounds().width) / 2,
        p.y = 400,
        p.visible = !1,
        d.addChild(p);
        var q = new a.EndPage;
        q.name = "endPage",
        q.visible = !1,
        d.addChild(q);
        var r = new a.RankPage;
        r.name = "rankPage",
        r.visible = !1,
        d.addChild(r);
        var u = new a.TicketPage;
        u.name = "ticketPage",
        u.visible = !1,
        d.addChild(u);
        var v = new a.RulePage;
        v.name = "rulePage",
        v.visible = !1,
        d.addChild(v);
        var z = new a.BottomBar;
        z.name = "bottomBar",
        d.addChild(z);
        var A = new a.StartPage;
        d.addChild(A);
        var B = new a.PopupPage("regFrame");
        B.name = "popupPage",
        B.visible = !1,
        d.addChild(B);
        var D = new a.GuidePage("follow");
        D.name = "guidePage",
        D.visible = !1,
        d.addChild(D);
        var F = new a.PopupPage("exchangeFrame");
        F.name = "exchangePage",
        F.visible = !1,
        d.addChild(F);
        var I = new a.PopupPage("tooltipFrame");
        I.name = "tooltipPage",
        I.visible = !1,
        d.addChild(I),
        setInterval(G, 1e3);
        var J, K = 0;
        i.on("mousedown",
        function(a) {
            H(!0),
            y ? APP.mustFollow && !APP.followed ? (J = 0, C("follow", "bottomBar")) : APP.REG_FIRST && APP.uid <= 0 ? (J = 0, P("popupPage", !0)) : J = a.localY: (J = 0, w = 3)
        }),
        i.on("pressmove",
        function() {
            1 == w && J > 0 && (L(), w = 2)
        }),
        i.on("pressup",
        function(a) {
            J && 3 != w && J - a.localY > 50 && (K = l[k[s % j]], x || (t += K), s++, i.playAnimation(), b.Sound.play("count", !0))
        }),
        b.Ticker.addEventListener("tick",
        function() {
            o.getChildByName("sum").text = h + 10 * t.toFixed(1) / 10,
            i.genCurrency()
        })
    }
    function E() {
        for (var a = 0; o > a; a++) {
            m[a] = [];
            for (var c = 0; n > c; c++) {
                var h = new b.Bitmap(e.getResult("d0"));
                h.regX = h.getBounds().width / 2,
                h.regY = h.getBounds().height / 2,
                h.x = I(f),
                h.y = -g / 2 + I(g),
                h.visible = !1,
                m[a].push(h),
                d.addChild(m[a][c])
            }
        }
    }
    function F() {
        var a = d.canvas,
        b = window.innerWidth,
        c = window.innerHeight - 3,
        e = f * c / g;
        i || (b >= e ? (b = e, d.x = 0) : d.x = (b - e) / 2),
        a.width = f,
        a.height = g,
        a.style.width = Math.min(b, 410) + "px",
        a.style.height = c + "px"
    }
    function G() {
        for (var a, c = 0; n > c; c++) a = m[r][c],
        a.visible = !0,
        b.Tween.get(a).to({
            x: I(f),
            y: g + a.getBounds().height / 2 + 100,
            rotation: 720 + I(400)
        },
        1e3 + I(800)).to({
            visible: !1
        },
        10).to({
            x: I(f),
            y: -g / 2 + I(g / 2),
            rotation: 0
        },
        10);
        r++,
        r %= o
    }
    function H(a) {
        var b = "";
        return APP.end ? (b = "亲，好可惜，活动已经结束了，下次早点来哦~", y = !1) : a && (APP.remainAllTimes <= 0 ? (b = "亲，好可惜，游戏次数已经用完啦，请关注后续活动吧~", y = !1) : APP.remainDayTimes <= 0 && (b = "亲，好可惜，今天的游戏次数已经用完啦，明天再来挑战吧！", y = !1)),
        y || (APP.showTip(b), P("tooltipPage", !0)),
        y
    }
    function I(a) {
        return parseInt(Math.random() * a)
    }
    function L() {
        d.getChildByName("arrow").visible = !1,
        v = setInterval(function() {
            u > 0 ? (u--, d.getChildByName("sumInfo").getChildByName("time").text = u + '"') : (APP.uid <= 0 ? P("popupPage", !0) : (x = !0, APP.getScore(null, !0)), O(), clearInterval(v), v = null)
        },
        1e3)
    }
    function M() {
        d.getChildByName("rankPage").visible = !1,
        d.getChildByName("ticketPage").visible = !1,
        d.getChildByName("rulePage").visible = !1
    }
    function N() {
        d.getChildByName("endPage").visible = !1,
        d.getChildByName("rulePage").visible = !1,
        d.getChildByName("rankPage").visible = !1,
        d.getChildByName("ticketPage").visible = !1,
        d.getChildByName("arrow").visible = !0,
        t = 0,
        s = 0,
        u = APP.IIMEOUT,
        d.getChildByName("sumInfo").getChildByName("time").text = u + '"',
        w = 1,
        x = !1
    }
    function O() {
        w = 3,
        d.getChildByName("rulePage").visible = !1,
        d.getChildByName("rankPage").visible = !1,
        d.getChildByName("ticketPage").visible = !1
    }
    function P(a, b) {
        var f, c = d.getChildByName(a),
        e = d.getChildByName("bottomBar");
        return c && (c.visible = b, f = c.getChildByName("frame"), f && (f.visible = b, f.htmlElement.style.display = b ? "block": "none"), e.getChildByName("domBar").htmlElement.style.display = b && "ticketPage" != a ? "none": "block"),
        c
    }
    function Q() {
        APP.loadRule(),
        d.getChildByName("rulePage").visible = !0,
        d.getChildByName("rankPage").visible = !1,
        d.getChildByName("ticketPage").visible = !1
    }
    function R() {
        var a = d.getChildByName("rankPage");
        APP.loadMore(a, 0),
        a.visible = !0,
        d.getChildByName("rulePage").visible = !1,
        d.getChildByName("ticketPage").visible = !1
    }
    function S() {
        var a = d.getChildByName("ticketPage");
        APP.loadMore(a, 0),
        a.visible = !0,
        d.getChildByName("rulePage").visible = !1,
        d.getChildByName("rankPage").visible = !1
    }
    var c, d, e, f = 640,
    g = 1e3,
    h = "\uffe5",
    i = b.Touch.isSupported(),
    j = 0,
    k = [],
    l = [],
    m = [],
    n = 5,
    o = APP.currency.length,
    p = o,
    q = 350,
    r = 0,
    s = 0,
    t = 0,
    u = APP.IIMEOUT,
    v = null,
    w = 0,
    x = !1,
    y = !0;
    a.Game = function(a, b) {
        d = a,
        e = b || {},
        e.getResult = function(a) {
            return e[a]
        },
        B(),
        F(),
        D()
    },
    a.Game.prototype.go = function(a, b) {
        P(a, !!b)
    },
    a.Game.prototype.end = function(a) {
        P("endPage", a),
        a ? M() : N()
    },
    a.Game.prototype.showTicket = function() {
        S()
    },
    a.Game.prototype.showGuide = C,
    a.Game.prototype.getSum = function() {
        return 10 * t.toFixed(1) / 10
    },
    a.Game.prototype.isIng = function() {
        return null != v
    },
    a.Game.prototype.abort = function() {
        clearInterval(v),
        N()
    },
    a.Game.prototype.checkPlayable = H,
    window.onresize = F,
    (a.StartPage = function() {
        var c = this;
        c.initialize();
        var h = new b.Bitmap(e.getResult("splash"));
        c.addChild(h);
        var i = new a.StartTip;
        i.x = (f - i.getBounds().width) / 2,
        i.y = 40,
        c.addChild(i);
        var j = new b.Bitmap(e.getResult("splashtitle"));
        j.x = (f - j.getBounds().width) / 2,
        j.y = 100,
        c.addChild(j);
        var k = new b.Bitmap(e.getResult("mb1000")),
        l = g - 300;
        k.x = (h.getBounds().width - k.getBounds().width) / 2,
        k.y = l,
        c.addChild(k);
        var m = new b.Bitmap(e.getResult("starttip"));
        m.x = (h.getBounds().width - m.getBounds().width) / 2,
        m.y = g - 400,
        c.addChild(m);
        var n;
        k.on("mousedown",
        function(a) {
            n = a.localY
        }),
        k.on("pressmove",
        function(a) {
            var b = a.currentTarget,
            c = a.localY - n;
            b.y + c < l && (b.y += c)
        }),
        k.on("pressup",
        function(a) {
            b.Sound.play("count", !0);
            var e = d.getChildByName("bottomBar");
            e.getChildByName("domBar").htmlElement.style.display = "block";
            var f = a.currentTarget;
            b.Tween.get(f).to({
                y: -g
            },
            400).call(function() {
                N(),
                c.visible = !1
            })
        }),
        h.on("mousedown",
        function() {})
    }).prototype = c = new b.Container,
    (a.StartTip = function() {
        var a = this;
        a.initialize();
        var c = new b.Text("已经有", "38px Arial", "white");
        a.addChild(c);
        var d = new b.Text((APP.userCount || 0) + "", "bold 40px Arial", "#ffff00");
        d.x = c.getBounds().width,
        d.y = -3,
        a.addChild(d);
        var e = new b.Text("人参加了疯狂划算", "38px Arial", "white");
        e.x = d.x + d.getBounds().width,
        a.addChild(e)
    }).prototype = c = new b.Container,
    (a.BottomBar = function() {
        var a = this;
        a.initialize();
        var c = new b.DOMElement("bottomBar");
        c.name = "domBar",
        a.addChild(c),
        $("#bottomBar").delegate("li", "click",
        function() {
            var b = $(this).attr("seed");
            switch (b) {
            case "1":
                R();
                break;
            case "2":
                S();
                break;
            case "3":
                3 == w && N(),
                d.getChildByName("rankPage").visible = !1,
                d.getChildByName("rulePage").visible = !1,
                d.getChildByName("ticketPage").visible = !1;
                break;
            case "4":
                Q()
            }
        })
    }).prototype = c = new b.Container,
    c.reload = function() {
        var b = this;
        b.visible = !0
    },
    (a.PlayerCurrency = function() {
        var a = this;
        a.initialize();
        var c = new b.Bitmap(e.getResult("mb0"));
        c.regX = c.getBounds().width / 2,
        c.regY = c.getBounds().height / 2,
        c.y = q,
        a.x = f / 2,
        a.y = g / 2 - 150,
        a.addChild(c);
        for (var d = null,
        h = 0; o > h; h++) d = new b.Bitmap(e.getResult("m0")),
        d.regX = d.getBounds().width / 2,
        d.regY = d.getBounds().height / 2,
        d.y = q,
        d.visible = !1,
        a.addChild(d)
    }).prototype = c = new b.Container,
    c.playAnimation = function() {
        var a = this,
        c = a.children[p];
        c.visible = !0,
        b.Tween.get(c).to({
            y: -g,
            scaleX: .5,
            scaleY: .5
        },
        300).to({
            y: q,
            scaleX: 1,
            scaleY: 1,
            visible: !1
        },
        0);
        var e = d.getChildByName("cloneLogo");
        e.visible = !0,
        b.Tween.get(e).to({
            y: -344
        },
        300).to({
            y: 344,
            visible: !1
        },
        0),
        p--,
        1 > p && (p = o)
    },
    c.genCurrency = function() {
        var a = this,
        b = k[s % j],
        c = e.getResult("mb" + b),
        d = e.getResult("m" + b),
        f = e.getResult("d" + b);
        a.children[0].image = c;
        for (var g = 1; o >= g; g++) a.children[g].image = d;
        for (var h = 0,
        i = m.length; i > h; h++) for (var l = 0,
        n = m[h].length; n > l; l++) m[h][l].image = f
    },
    (a.SumInfo = function() {
        var a = this;
        a.initialize();
        var c = new b.Bitmap(e.getResult("tmbg"));
        c.x = (f - c.getBounds().width) / 2,
        c.y = 30,
        a.addChild(c);
        var d = new b.Text(h + t, "bold 46px Arial", "yellow");
        d.name = "sum",
        d.textAlign = "center",
        d.textBaseline = "middle",
        d.x = f / 2,
        d.y = c.y + c.getBounds().height / 2,
        a.addChild(d);
        var g = c.clone();
        g.scaleX = .7,
        g.x = (f - .7 * c.getBounds().width) / 2,
        g.y = c.y + c.getBounds().height + 15,
        a.addChild(g);
        var i = new b.Bitmap(e.getResult("tmicon"));
        i.x = g.x + 14,
        i.y = g.y + 14,
        a.addChild(i);
        var j = new b.Text(u + '"', "bold 44px Arial", "white");
        j.name = "time",
        j.textAlign = "center",
        j.textBaseline = "middle",
        j.x = f / 2 + i.getBounds().width / 2,
        j.y = g.y + g.getBounds().height / 2,
        a.addChild(j)
    }).prototype = c = new b.Container,
    (a.EndPage = function() {
        var c = this;
        c.initialize();
        var d = new a.Mask(.7);
        c.addChild(d);
        var e = new b.DOMElement("getFrame");
        e.name = "frame",
        e.visible = !1,
        c.addChild(e)
    }).prototype = c = new b.Container,
    c.reload = function(a) {
        var b = this;
        APP.getScore(a),
        b.visible = !0
    },
    (a.Mask = function(a) {
        var c = this;
        c.initialize();
        var d = new b.Shape;
        d.graphics.beginFill("black").rect(0, 0, f, g),
        d.alpha = a,
        c.addChild(d)
    }).prototype = c = new b.Container,
    (a.RulePage = function() {
        var c = this;
        c.initialize();
        var d = new b.Shape;
        d.graphics.beginFill("#d8fee9").rect(0, 0, f, g),
        c.addChild(d);
        var e = new b.DOMElement("ruleFrame");
        e.name = "frame",
        c.addChild(e);
        var h = new a.Copyright("#0b5c31");
        h.y = 780,
        c.addChild(h),
        d.on("mousedown",
        function() {})
    }).prototype = c = new b.Container,
    (a.RankPage = function() {
        var c = this;
        c.initialize();
        var d = new b.Shape;
        d.graphics.beginFill("#0b5c31").rect(0, 0, f, g),
        c.addChild(d);
        var e = new b.DOMElement("rankFrame");
        e.name = "frame",
        c.addChild(e);
        var h = new a.RankUser;
        h.name = "user",
        h.y = 730,
        h.visible = !1,
        c.addChild(h);
        var i = new a.Copyright;
        i.y = 780,
        c.addChild(i),
        d.on("mousedown",
        function() {})
    }).prototype = c = new b.Container,
    c.reload = function(a) {
        var b = this;
        if (a) {
            for (var c = a.result || [], d = a.max || 0, e = a.rank || 0, f = [], g = "<tr><td>{rank}</td><td>{name}</td><td>{sum}</td></tr>", i = "", j = 0, k = c.length; k > j; j++) i = g,
            i = i.replace(/\{rank\}/, c[j].rank),
            i = i.replace(/\{name\}/, c[j].name),
            i = i.replace(/\{sum\}/, h + c[j].sum),
            f.push(i);
            var l = $("#J_RankTable");
            a.page > 1 ? l.find("tbody").append(f.join("")) : l.find("tbody").html(f.join("")),
            0 >= k ? l.next(".loading").text("没有更多了").show() : l.next(".loading").hide();
            var m = b.getChildByName("user");
            APP.uid > 0 && m && (m.getChildByName("userMax").text = "最佳成绩：" + h + d, m.getChildByName("userRank").text = "当前排名：" + e + "位", m.getChildByName("userRank").x = 420 - 15 * (e + "").length, m.visible = !0),
            APP.uMax = d,
            APP.uRank = e
        }
    },
    (a.RankUser = function() {
        var a = this;
        a.initialize();
        var c = new b.Text("", "30px Arial", "white");
        c.name = "userMax",
        c.x = 30,
        a.addChild(c);
        var d = new b.Text("", "30px Arial", "white");
        d.name = "userRank",
        d.x = 420,
        a.addChild(d)
    }).prototype = c = new b.Container,
    (a.TicketPage = function() {
        var c = this;
        c.initialize();
        var d = new b.Shape;
        d.graphics.beginFill("#0b5c31").rect(0, 0, f, g),
        c.addChild(d);
        var e = new b.DOMElement("ticketFrame");
        e.name = "frame",
        c.addChild(e);
        var h = new a.TicketEmpty;
        h.name = "empty",
        h.visible = !1,
        c.addChild(h);
        var i = new a.Copyright;
        i.y = 780,
        c.addChild(i),
        d.on("mousedown",
        function() {})
    }).prototype = c = new b.Container;
    var U = '<li data-id="{id}"><div class="top-wrap"><div class="num-wrap"><em>{sum}</em></div><div class="info-wrap"><div class="type">{type}</div><div class="sn">{sn}</div></div><span class="arrow-btn" seed="arrow"></span></div><div class="detail-wrap"><p><label>使用规则</label><span class="dtl-val">{rule}</span></p><p><label>有效期</label><span class="dtl-val">{time}</span></p><p><label>状态</label><span class="dtl-val status">{status}</span></p><div class="button-wrap"><button class="exchange" type="button" seed="exchange" style="{exchange}">立即兑换</button><button class="use" type="button" seed="use" data-url="{url}" style="{use}">立即使用</button></div></div></li>';
    c.reload = function(a) {
        var b = this;
        if (a) {
            for (var c = a.result || [], d = c.length, e = a.remain || "0", f = a.sum || 0, g = [], h = "", i = 0; d > i; i++) h = U,
            h = h.replace(/\{id\}/, c[i].id),
            h = h.replace(/\{sum\}/, c[i].sum),
            h = h.replace(/\{type\}/, c[i].type),
            h = h.replace(/\{sn\}/, c[i].sn),
            h = h.replace(/\{rule\}/, c[i].rule),
            h = h.replace(/\{time\}/, c[i].time),
            h = h.replace(/\{status\}/, c[i].status),
            h = h.replace(/\{exchange\}/, c[i].exchange && !c[i].useable ? "display:block;": "display:none;"),
            h = h.replace(/\{use\}/, c[i].useable ? "display:block;": "display:none;"),
            c[i].useable && (h = h.replace(/\{url\}/, c[i].shopUrl)),
            g.push(h);
            var j = $("#J_TicketList");
            a.page > 1 ? j.append(g.join("")) : j.html(g.join("")),
            0 >= d ? j.children("li").length ? (b.getChildByName("empty").visible = !1, j.next(".loading").text("没有更多了").show()) : (b.getChildByName("empty").visible = !0, j.next(".loading").hide()) : (b.getChildByName("empty").visible = !1, j.next(".loading").hide()),
            e > 0 && a.total_remain > 0 ? $("#J_RemainTicket").text(e).parent().show() : $("#J_RemainTicket").parent().hide(),
            b.sum = f
        }
    },
    (a.TicketEmpty = function() {
        var a = this;
        a.initialize();
        var c = new b.Bitmap(e.getResult("ticketEmpty"));
        c.x = (f - c.getBounds().width) / 2,
        c.y = (g - c.getBounds().height - 100) / 2,
        a.addChild(c);
        var d = new b.Bitmap(e.getResult("lineArrow"));
        d.x = (f - d.getBounds().width) / 2,
        d.y = 3 * c.y / 2,
        a.addChild(d),
        b.Tween.get(d, {
            loop: !0
        }).to({
            y: d.y + 20
        },
        500).to({
            y: d.y - 20
        },
        500)
    }).prototype = c = new b.Container,
    (a.PopupPage = function(c) {
        var d = this;
        d.initialize();
        var e = new a.Mask(.85);
        d.addChild(e);
        var f = new b.DOMElement(c);
        f.name = "frame",
        d.addChild(f)
    }).prototype = c = new b.Container,
    (a.GuidePage = function(c) {
        var f = this;
        f.initialize();
        var g = new a.Mask(.85);
        f.addChild(g);
        var h = new b.Bitmap(e.getResult(c));
        h.name = "img",
        f.addChild(h),
        f.on("click",
        function() {
            f.visible = !1;
            var a = f.fromPage;
            a && d.getChildByName(a).reload(APP.timestamp)
        })
    }).prototype = c = new b.Container,
    (a.Copyright = function(a) {
        var c = this;
        c.initialize();
        var d = new b.Text(APP.copyright || "\xa9" + APP.customer, "28px Arial", a || "white");
        d.textAlign = "center",
        d.x = f / 2,
        c.addChild(d)
    }).prototype = c = new b.Container
} (lib = lib || {},
createjs = createjs || {});
var lib, createjs;