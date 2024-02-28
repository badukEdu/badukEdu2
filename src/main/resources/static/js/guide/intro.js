window.addEventListener("DOMContentLoaded", function(){
    const swiper = new Swiper(".mySwiper", {
      spaceBetween: 30,
      centeredSlides: true,
      autoplay: {
        delay: 2000,
        disableOnInteraction: false,
 loop:true,
 },
 pagination: {
        el: ".swiper-pagination",
         clickable: true,


      },

navigation: {
  nextEl: ".mySwiper .next",
  prevEl: ".mySwiper .prev",
},

});

});