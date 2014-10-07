/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
    if ($("#user-profile").length) {
      var t = $(".tabs a"),
              e = $(".tab-content .tab");
      t.click(function(n) {
        n.preventDefault();
        var i = t.index(this);
        t.removeClass("active"), t.eq(i).addClass("active"), e.removeClass("active"), e.eq(i).addClass("active");
      });
    }
  };
  $(document).on("ready page:load", t);
}(),
        function() {
          var t = function() {
            if ($("#wizard").length) {
              var t = $(".form-wizard .step"),
                      e = t.find("[data-step]"),
                      n = $(".header .steps .step"),
                      i = 0;
              e.click(function(e) {
                e.preventDefault();
                var s = $(this).data("step") - 1,
                        r = s > i ? "fadeInRightStep" : "fadeInLeftStep",
                        o = "fadeInRightStep" === r ? "fadeOutLeftStep" : "fadeOutRightStep",
                        a = t.eq(i);
                a.on(utils.animation_ends(), function() {
                  a.removeClass("fadeInRightStep fadeInLeftStep fadeOutRightStep fadeOutLeftStep");
                }).addClass(o),
                        i = s,
                        n.removeClass("active").filter(":lt(" + (i + 1) + ")").addClass("active"),
                        t.removeClass("active"), t.eq(s).addClass("active animated " + r);
              });
            }
          };
          $(document).on("ready page:load", t);
        }();


