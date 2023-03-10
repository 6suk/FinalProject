let trade_type_val = $('#trade_type').val();
let trade_type_1 = $('#trade_type_1');
let trade_type_2 = $('#trade_type_2');

let tel_type_val = $('#tel_type').val();
let tel_type_1 = $('#tel_type_1');
let tel_type_2 = $('#tel_type_2');

$(function () {
  $('[LIKE]').each(function (index, item) {
    let like = $(item).attr('LIKE');
    if (like === '0') {
      $(item).attr('class', 'like fa-regular fa-heart');
    } else {
      $(item).attr('class', 'like fa-solid fa-heart');
    }
  });

  // 거래 방식에 맞춰 태그 변경
  switch (trade_type_val) {
    case '1': // 직접거래
      $(trade_type_1).show();
      break;
    case '2': // 만남거래
      $(trade_type_2).show();
      break;
    case '3': // 직접거래 + 만남거래
      $(trade_type_1).show();
      $(trade_type_2).show();
      break;
  }

  // 연락 방법에 맞춰 태그 변경
  switch (tel_type_val) {
    case '1': // 오픈채팅
      $(tel_type_1).show();
      break;
    case '2': // 댓글
      $(tel_type_2).show();
      break;
  }
});

$('[LIKE]').click(function (event) {
  // event.preventDefault();
  // event.stopPropagation();

  let like = $(this).attr('LIKE');
  let mid = $(this).attr('id');
  let like_index = $(this).parent().next();
  let cnt = $(like_index).text();

  if (like === '0') {
    $(this).attr('class', 'like fa-solid fa-heart');
    $(like_index).text(Number(cnt) + 1);
    likePress(mid);
  }
  if (like === '1') {
    $(this).attr('class', 'like fa-regular fa-heart');
    $(like_index).text(Number(cnt) - 1);
    likeDel(mid);
  }
});

function likePress(mid) {
  $.ajax({
    type: 'GET',
    url: '/mate/like/' + mid,
    success: function (data) {
      if (data) {
        $('#' + mid).attr('LIKE', 1);
      }
    },
  });
}
function likeDel(mid) {
  $.ajax({
    type: 'GET',
    url: '/mate/delLike/' + mid,
    success: function (data) {
      if (data) {
        $('#' + mid).attr('LIKE', 0);
      }
    },
  });
}
