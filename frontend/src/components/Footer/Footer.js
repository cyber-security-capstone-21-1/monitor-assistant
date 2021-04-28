import React from 'react';
import "./Footer.scss";

function Footer (props) {
  return (
    <footer>
      <div className="row">
        <div className="container">
          <div className="pull-left">
            <div className="symbol"></div>
          </div>
          <div className="pull-left">
            <p id="footer__title">
              <span>사이버수사관 모니터링업무보조시스템</span><br/>
              <span><small>CYBER CRIME MONITORING ASSISTANCE PROGRAM</small></span>
            </p>
            <div className="divider"></div>
            <div className="notice">
              <span>&copy; 경찰청. 시스템의 외부 유출을 금합니다.</span>
            </div>
          </div>
          <div className="pull-right">
            <span id="footer__creator">사이버보안캡스톤디자인 5조</span>
          </div>
        </div>
      </div>
    </footer>
  );
}

export default Footer;
  