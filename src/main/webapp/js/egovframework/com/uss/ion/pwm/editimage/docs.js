$(function() {

  var console = window.console || {log: function () {}};

  // Overview
  // -------------------------------------------------------------------------

  (function() {
    var $image = $(".eg-wrapper img"),
        $dataX = $("#dataX"),
        $dataY = $("#dataY"),
        $dataHeight = $("#dataHeight"),
        $dataWidth = $("#dataWidth"),
        options = {
          aspectRatio: NaN,
          data: {
            x: 480,
            y: 60
          },
          preview: ".img-preview",
          done: function(data) {
            $dataX.val(data.x);
            $dataY.val(data.y);
            $dataHeight.val(data.height);
            $dataWidth.val(data.width);
          }
        };


    $(document).on("click", "[data-method]", function () {
      var data = $(this).data();

      if (data.method) {
        $image.cropper(data.method, data.option);
      }
    });

    var $inputImage = $("#inputImage");

    if (window.FileReader) {

      $inputImage.change(function() {
        var fileReader = new FileReader(),
            files = this.files,
            file;

        if (files.length) {
          file = files[0];

          if (/^image\/\w+$/.test(file.type)) {
            fileReader.readAsDataURL(file);
            fileReader.onload = function () {
              $image.cropper("reset", true).cropper("replace", this.result);
            };
          }
        }
      });
    } else {
      $inputImage.addClass("hide");
    }

  

    $("[data-toggle='tooltip']").tooltip();
  }());

  // Sidebar
  // -------------------------------------------------------------------------

  (function() {
    var $sidebar = $(".docs-sidebar"),
        offset = $sidebar.offset();
        offsetTop = offset.top;
        mainHeight = $sidebar.parents(".row").height() - $sidebar.height();

    $(window).bind("scroll", function() {
      var st = $(this).scrollTop();

      if (st > offsetTop && (st - offsetTop) < mainHeight) {
        $sidebar.addClass("fixed");
      } else {
        $sidebar.removeClass("fixed");
      }
    });
  }());

  // Examples
  // -------------------------------------------------------------------------

  // Example 1

});
