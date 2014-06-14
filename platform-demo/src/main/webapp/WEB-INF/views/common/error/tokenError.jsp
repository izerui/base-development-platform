<%--

    Copyright (C) 2014 serv (liuyuhua69@gmail.com)

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

            http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

--%>
<html>
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport" content="width=device-width" />
<style type="text/css">
body {
  padding-top: 60px;
  /* 60px to make the container go all the way to the bottom of the topbar */
}
</style>
<title>TokenError</title>
</head>
<body>
  <div class="container">
      <div class="col-sm-12">
        <p class="alert alert-danger">
          <spring:message code="exception.page.tokenerror" ></spring:message><br /> <a
            href="${pageContext.request.contextPath}" class="btn btn-default">Go
            to TOP</a>
        </p>
      </div>
    </div>
</body>
</html>