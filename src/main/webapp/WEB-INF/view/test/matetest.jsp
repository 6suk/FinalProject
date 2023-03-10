<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- //// MATE WRITE TEST PAGE //// -->
    
<!DOCTYPE html>
<html lang="ko">
  <head>
<!-- // Heading -->
<%@ include file="../common/heading.jsp"%>
<!-- // Heading -->
    <title>FinalProject</title>
  </head>
  <body>
    <!-- TOP -->
    <%@ include file="../common/top.jsp"%>
    <!-- TOP -->

    <div class="container-nonaside">
      <!-- content -->
      <section>
        <div class="content-title">
          <div class="form-tag" id="matecategory">
            <input type="radio" name="category" id="mate" value="0" />
            <label for="mate" id="category">조각 메이트</label>
            <input type="radio" name="category" id="ott" value="1" />
            <label for="ott" id="category">OTT 메이트</label>
          </div>
          <input type="button" id="sendbtn" class="btn btn-main main" value="등록하기" />
        </div>

        <div class="writebox flex-lg-row">
          <div class="imgbox">
            <label class="imgbox-img label" id="label" for="input">
              <div class="inner" id="inner">
                <i class="fa-solid fa-camera"></i>
                <span class="graytxt pt-2">사진 올리기</span>
                <span class="l-graytxt font-12">(최대 5장)</span>
              </div>
            </label>
            <input id="input" class="input" accept="image/*" type="file" multiple="true" hidden="true" name="img" />
            <div class="preview" id="preview" hidden></div>
          </div>
          <form id="mateform" class="inputbox">
          <div class="inputbox">
            <input type="text" class="form-control text" placeholder="지역*" name="area" />
            <input class="form-control text" type="text" name="title" placeholder="제목*" />
            <input class="form-control text" type="number" name="price1" placeholder="구매가격*" />
            <input class="form-control text" type="number" name="price2" placeholder="판매가격*" />
            <textarea class="form-control" name="content" cols="30" rows="10" placeholder="본문"></textarea>
          </div>
        </div>
      </form>
      </section>
    </div>
  </body>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/Sortable/1.14.0/Sortable.min.js"></script>
  <script src="/js/upload.js"></script>
</html>



