import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PropertySet from './property-set';
import PropertySetDetail from './property-set-detail';
import PropertySetUpdate from './property-set-update';
import PropertySetDeleteDialog from './property-set-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PropertySetDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PropertySetUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PropertySetUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PropertySetDetail} />
      <ErrorBoundaryRoute path={match.url} component={PropertySet} />
    </Switch>
  </>
);

export default Routes;
