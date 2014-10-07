var ready = function () {
    Skins.initialize(), Sidebar.initialize(), UI.smart_selects();
};
$(document).on("ready page:load", ready), $(document).on("page:change", function () {
    window.prevPageYOffset = window.pageYOffset, window.prevPageXOffset = window.pageXOffset;
}), $(document).on("page:load", function () {
    $(".fix-scroll").length > 0 && $(".fix-scroll").hide().show();
});
var UI = {
    smart_selects: function() {
        var t = $("[data-smart-select]");
        $.each(t, function(t, e) {
            var n = $(e);
            if (n.parent().hasClass("fake-select-wrap"))
                return void n.siblings(".fake-select").html(n.find("option:selected").text());
            var i = $("<div class='fake-select-wrap' />"),
                    s = $("<div class='fake-select'></div>");
            n.wrap(i), n.after(s), s.html(n.find("option:selected").text()), n.change(function() {
                s.html($(this).find("option:selected").text());
            }), n.focus(function() {
                s.addClass("focus");
            }).focusout(function() {
                s.removeClass("focus");
            });
        });
    }
},
Skins = {
    initialize: function() {
        var t = $(".skin-switcher .toggler"),
                e = $(".skin-switcher .menu"),
                n = $(".main-sidebar");
        t.length && ($.cookie("current_skin") && (n.attr("id", $.cookie("current_skin")), e.find("a").removeClass("active"), e.find("a[data-skin=" + $.cookie("current_skin") + "]").addClass("active")), t.click(function(t) {
            t.stopPropagation(), e.toggleClass("active");
        }), $("body").click(function() {
            e.removeClass("active");
        }), e.click(function(t) {
            t.stopPropagation();
        }), e.find("a").click(function(t) {
            t.preventDefault();
            var i = $(this).data("skin");
            e.find("a").removeClass("active"), $(this).addClass("active"), n.attr("id", i), $.removeCookie("current_skin", {
                path: "/"
            }), $.cookie("current_skin", i, {
                path: "/"
            });
        }));
    }
},
Sidebar = {
    initialize: function() {
        var t = $(".main-sidebar"),
                e = t.find(".current-user .menu");
        $(".current-user .name").click(function(t) {
            t.preventDefault(), t.stopPropagation(), e.toggleClass("active");
        }), e.click(function(t) {
            t.stopPropagation();
        }), $("body").click(function() {
            e.removeClass("active");
        });
        var n = t.find("[data-toggle~='sidebar']");
        n.click(function(t) {
            if (t.preventDefault(), !utils.isTablet()) {
                $(this).closest(".submenu").length || n.not(this).removeClass("toggled").siblings(".submenu").slideUp(300, o);
                var e = $(this),
                        i = $(this).siblings(".submenu");
                e.toggleClass("toggled"), e.hasClass("toggled") ? i.slideDown(300, o) : i.slideUp(300, o);
            }
        });
        var i = window.location.pathname;
        t.find(".menu-section a").removeClass("active");
        var s = t.find("a[href='" + i + "']");
        if (s.length) {
            if (s.addClass("active"), s.parents(".submenu").length) {
                var r = s.closest(".option").find("[data-toggle~='sidebar']");
                r.addClass("active toggled"), s.parents(".submenu").addClass("active");
            }
        } else
            t.find(".menu-section .option > a:eq(0)").addClass("active");
        var o = function() {
            var t = $("body").height();
            $(".main-sidebar").css("bottom", "auto");
            var e = $(".main-sidebar").height();
            t > e ? $(".main-sidebar").css("bottom", 0) : $(".main-sidebar").css("bottom", "auto");
        },
                a = $("#content .sidebar-toggler");
        a.click(function(t) {
            t.stopPropagation(), $("body").toggleClass("open-sidebar");
        }), $("#content").click(function() {
            $("body").removeClass("open-sidebar");
        });
    }
};
window.utils = {
    isFirefox: function() {
        return navigator.userAgent.toLowerCase().indexOf("firefox") > -1;
    },
    animation_ends: function() {
        return "animationend webkitAnimationEnd oAnimationEnd";
    },
    isTablet: function() {
        return $(".main-sidebar").width() < 100;
    },
    get_timestamp: function(t) {
        return moment().subtract("days", t).toDate().getTime();
    }
},
function() {
    var t = function() {
        $("body#sidebar").length && ($("html, body").css("height", "100%"), $(".main-sidebar").wrapInner("<div class='scroll-wrapper'></div>"));
    };
    $(document).on("ready page:load", t);
}();