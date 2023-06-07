$(document).ready(function() {
    var slider = $(".slider");
    var slideWidth = $(".card-product").outerWidth() + 20; // 20 for margin
    var slideIndex = 0;

    var totalSlides = $(".card-product").length;

    $(".slider-next-btn").click(function() {
        slideIndex++;
        if (slideIndex >= totalSlides) {
            slideIndex = 0;
        }
        updateSliderPosition();
    });

    $(".slider-prev-btn").click(function() {
        slideIndex--;
        if (slideIndex < 0) {
            slideIndex = totalSlides - 1;
        }
        updateSliderPosition();
    });

    $(".slider").on("mousedown", function(event) {
        var startX = event.pageX;
        var currentTranslateX = -slideWidth * slideIndex;

        $(document).on("mousemove", function(event) {
            var moveX = event.pageX - startX;
            slider.css("transform", "translateX(" + (currentTranslateX + moveX) + "px)");
        });

        $(document).on("mouseup", function() {
            $(document).off("mousemove");
            $(document).off("mouseup");

            var endX = event.pageX;
            var distanceX = endX - startX;

            if (distanceX > slideWidth / 2) {
                slideIndex--;
                if (slideIndex < 0) {
                    slideIndex = totalSlides - 1;
                }
            } else if (distanceX < -slideWidth / 2) {
                slideIndex++;
                if (slideIndex >= totalSlides) {
                    slideIndex = 0;
                }
            }

            updateSliderPosition();
        });
    });

    function updateSliderPosition() {
        slider.css("transform", "translateX(" + (-slideWidth * slideIndex) + "px)");
    }

    function resetSliderPosition() {
        slideIndex = 0;
        updateSliderPosition();
    }

    $(".slider-next-btn").on("click", function() {
        if (slideIndex === totalSlides - 1) {
            resetSliderPosition();
        }
    });
});