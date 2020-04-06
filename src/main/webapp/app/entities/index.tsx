import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import DataMaster from './data-master';
import Data from './data';
import Classification from './classification';
import PropertySet from './property-set';
import Property from './property';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}data-master`} component={DataMaster} />
      <ErrorBoundaryRoute path={`${match.url}data`} component={Data} />
      <ErrorBoundaryRoute path={`${match.url}classification`} component={Classification} />
      <ErrorBoundaryRoute path={`${match.url}property-set`} component={PropertySet} />
      <ErrorBoundaryRoute path={`${match.url}property`} component={Property} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
