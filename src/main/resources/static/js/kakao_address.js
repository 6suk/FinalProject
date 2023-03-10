// JOIN
function joinGetAddress() {
  new daum.Postcode({
    oncomplete: function (data) {
      // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

      // 각 주소의 노출 규칙에 따라 주소를 조합한다.
      // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
      var addr = ''; // 주소 변수
      var extraAddr = ''; // 참고항목 변수

      //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
      if (data.userSelectedType === 'R') {
        // 사용자가 도로명 주소를 선택했을 경우
        addr = data.roadAddress;
      } else {
        // 사용자가 지번 주소를 선택했을 경우(J)
        addr = data.jibunAddress;
      }

      // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
      if (data.userSelectedType === 'R') {
        // 법정동명이 있을 경우 추가한다. (법정리는 제외)
        // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
        if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
          extraAddr += data.bname;
        }
        // 건물명이 있고, 공동주택일 경우 추가한다.
        if (data.buildingName !== '' && data.apartment === 'Y') {
          extraAddr += extraAddr !== '' ? ', ' + data.buildingName : data.buildingName;
        }
        // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
        if (extraAddr !== '') {
          extraAddr = ' (' + extraAddr + ')';
        }
        // 조합된 참고항목을 해당 필드에 넣는다.
        document.getElementById('extraAddr').value = extraAddr;
      } else {
        document.getElementById('extraAddr').value = '';
      }

      // 우편번호와 주소 정보를 해당 필드에 넣는다.
      document.getElementById('postcode').value = data.zonecode;
      document.getElementById('addr').value = addr;
      // 커서를 상세주소 필드로 이동한다.
      document.getElementById('detailAddr').focus();
    },
  }).open();
}

// MATE_WRITE
// 우편번호 찾기 찾기 화면을 넣을 element
var element_wrap = document.getElementById('wrap');
var section_wrap = document.getElementById('section-wrap');
let find_btn = document.getElementById('addr-find');
let cancel_btn = document.getElementById('addr-cancle');
let close_btn = document.getElementById('addr-close');
let modalbg = document.getElementById('modal-bg');

// inputValue
let place_name = document.getElementById('place_name');
let place_coords = document.getElementById('place_coords');
let place_code = document.getElementById('place_code');
let place_addr = document.getElementById('place_addr');

function foldDaumPostcode() {
  // iframe을 넣은 element를 안보이게 한다.
  element_wrap.style.display = 'none';
  find_btn.style.display = '';
}

//주소-좌표 변환 객체를 생성
var geocoder = new daum.maps.services.Geocoder();

function sample3_execDaumPostcode() {
  // 현재 scroll 위치를 저장해놓는다.
  var currentScroll = Math.max(document.body.scrollTop, document.documentElement.scrollTop);
  new daum.Postcode({
    oncomplete: function (data) {
      // 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

      // 각 주소의 노출 규칙에 따라 주소를 조합한다.
      // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
      var addr = ''; // 주소 변수

      //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
      if (data.userSelectedType === 'R') {
        // 사용자가 도로명 주소를 선택했을 경우
        addr = data.roadAddress;
      } else {
        // 사용자가 지번 주소를 선택했을 경우(J)
        addr = data.jibunAddress;
      }
      // 우편번호와 주소 정보를 해당 필드에 넣는다.
      place_addr.disabled = false;
      place_addr.value = addr;
      let addr_code = data.sigunguCode.substring(0, 2);

      // 시군구 코드
      place_code.disabled = false;
      place_code.value = addr_code;
      place_name.disabled = false;
      place_name.readOnly = true;

      if (data.buildingName != null && data.buildingName != '') {
        place_name.value = data.buildingName;
      } else {
        place_name.value = addr;
      }

      cancel_btn.style.display = '';
      find_btn.style.display = 'none';

      // iframe을 넣은 element를 안보이게 한다.
      // (autoClose:false 기능을 이용한다면, 아래 코드를 제거해야 화면에서 사라지지 않는다.)
      element_wrap.style.display = 'none';
      section_wrap.style.display = 'none';
      modalbg.style.display = 'none';

      // 우편번호 찾기 화면이 보이기 이전으로 scroll 위치를 되돌린다.
      document.body.scrollTop = currentScroll;

      geocoder.addressSearch(data.address, function (results, status) {
        // 정상적으로 검색이 완료됐으면
        if (status === daum.maps.services.Status.OK) {
          var result = results[0]; //첫번째 결과의 값을 활용

          // 해당 주소에 대한 좌표
          let lat = result.y;
          let lng = result.x;
          var coords = lat + ' ' + lng;
          place_coords.disabled = false;
          place_coords.value = coords;
        }
      });
    },
    // 우편번호 찾기 화면 크기가 조정되었을때 실행할 코드를 작성하는 부분. iframe을 넣은 element의 높이값을 조정한다.
    onresize: function (size) {
      element_wrap.style.height = size.height + 'px';
    },
    width: '100%',
    height: '100%',
  }).embed(element_wrap, {
    autoClose: false,
  });

  // iframe을 넣은 element를 보이게 한다.
  element_wrap.style.display = 'block';
  section_wrap.style.display = 'block';
}

function addr_cancle() {
  cancel_btn.style.display = 'none';
  find_btn.style.display = '';

  place_addr.value = null;
  place_name.value = null;
  place_coords.value = null;
  place_code.value = null;

  place_addr.disabled = true;
  place_name.disabled = true;
  place_coords.disabled = true;
  place_code.disabled = true;
}

let modal_bg = $('.modalBg');
let modal = $('.savedot-modal-addr');
let addr_find = $('#addr-find');
let modal_close_btn = $('#closebtn');

addr_find.click(function () {
  modal_bg.show();
  modal.css('opacity', '1');
});

modal_close_btn.click(function () {
  modal_bg.hide();
  modal.css('opacity', '0');
});
