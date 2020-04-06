import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import DataMaster from './data-master';
import DataMasterDetail from './data-master-detail';
import DataMasterUpdate from './data-master-update';
import DataMasterDeleteDialog from './data-master-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={DataMasterDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DataMasterUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DataMasterUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DataMasterDetail} />
      <ErrorBoundaryRoute path={match.url} component={DataMaster} />
    </Switch>
  </>
);

export default Routes;
