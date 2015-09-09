/*
 * Copyright 2015 Nokia Solutions and Networks
 * Licensed under the Apache License, Version 2.0,
 * see licence.txt file for details.
 */
package org.robotframework.ide.eclipse.main.plugin.execution;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.robotframework.ide.eclipse.main.plugin.execution.ExecutionStatus.Status;

public class ExecutionViewContentProvider implements ITreeContentProvider {

    private boolean isFailedFilterEnabled;

    @Override
    public void dispose() {

    }

    @Override
    public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {

    }

    @Override
    public Object[] getElements(final Object inputElement) {

        return (ExecutionStatus[]) inputElement;
    }

    @Override
    public Object[] getChildren(final Object parentElement) {
        
        if (parentElement instanceof ExecutionStatus) {
            final List<ExecutionStatus> children = ((ExecutionStatus) parentElement).getChildren();

            if (isFailedFilterEnabled) {
                final List<ExecutionStatus> failedChildren = new ArrayList<ExecutionStatus>();
                for (ExecutionStatus executionStatus : children) {
                    if (executionStatus.getStatus() == Status.FAIL) {
                        failedChildren.add(executionStatus);
                    }
                }
                return failedChildren.toArray(new ExecutionStatus[failedChildren.size()]);
            }

            return children.toArray(new ExecutionStatus[children.size()]);
        }
        return null;
    }

    @Override
    public Object getParent(final Object element) {
        
        if (element instanceof ExecutionStatus) {
            return ((ExecutionStatus) element).getParent();
        }
        return null;
    }

    @Override
    public boolean hasChildren(final Object element) {
        
        if (element instanceof ExecutionStatus) {
            return ((ExecutionStatus) element).getChildren().size() > 0;
        }
        return false;
    }
    
    public void setFailedFilterEnabled(final boolean isFailedFilterEnabled) {
        this.isFailedFilterEnabled = isFailedFilterEnabled;
    }

    public void switchFailedFilter() {
        if (isFailedFilterEnabled) {
            isFailedFilterEnabled = false;
        } else {
            isFailedFilterEnabled = true;
        }
    }

}
