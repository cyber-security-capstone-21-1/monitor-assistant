import React, { Component } from 'react';
import "./Footer.scss";

class Footer extends Component {
    render() {
        return (
          <footer>
            <div className="row">
              <div className="container">
                <div className="pull-left">
                  <div className="symbol"></div>
                </div>
                <div className="pull-left">
                  <span id="footer__title">사이버수사관 모니터링업무보조시스템</span>
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
  }
  
export default Footer;
  