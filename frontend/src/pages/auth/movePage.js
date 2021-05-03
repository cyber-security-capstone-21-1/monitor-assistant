import React from 'react';

import { withRouter } from 'react-router';

const movePage = (props) => {
  // if (props.location !== prevProps.location) {
  //   window.scrollTo(0, 0)
  // }
  console.log('페이지를 이동했습니다.')
  return props.children;
};

export default withRouter(movePage);