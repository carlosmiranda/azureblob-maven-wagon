/**
 * Copyright (C) 2016 Carlos Miranda
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.github.carlosmiranda.azureblob;

import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import java.io.File;
import java.net.URISyntaxException;
import java.util.List;
import org.apache.maven.wagon.ConnectionException;
import org.apache.maven.wagon.ResourceDoesNotExistException;
import org.apache.maven.wagon.TransferFailedException;
import org.apache.maven.wagon.Wagon;
import org.apache.maven.wagon.authentication.AuthenticationException;
import org.apache.maven.wagon.authentication.AuthenticationInfo;
import org.apache.maven.wagon.authorization.AuthorizationException;
import org.apache.maven.wagon.events.SessionListener;
import org.apache.maven.wagon.events.TransferListener;
import org.apache.maven.wagon.proxy.ProxyInfo;
import org.apache.maven.wagon.proxy.ProxyInfoProvider;
import org.apache.maven.wagon.repository.Repository;

/**
 * Maven Wagon implementation that adds support for Azure Blob Storage.
 *
 * @author Carlos Miranda (miranda.cma+azureblob.wagon@gmail.com)
 */
final class AzureBlobWagon implements Wagon {
    /**
     * Cloud blob client.
     */
    private final CloudBlobContainer container;

    /**
     * Get instance using with the specified container.
     * @param container CloudBlob Container
     */
    AzureBlobWagon(final CloudBlobContainer container) {
        this.container = container;
    }

    @Override
    public void get(final String resourceName, final File destination)
            throws TransferFailedException, ResourceDoesNotExistException,
            AuthorizationException {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean getIfNewer(final String resourceName, final File destination,
        final long timestamp) throws TransferFailedException,
        ResourceDoesNotExistException, AuthorizationException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void put(final File source, final String destination)
        throws TransferFailedException, ResourceDoesNotExistException,
        AuthorizationException {
        // TODO Auto-generated method stub

    }

    @Override
    public void putDirectory(final File sourceDirectory,
        final String destinationDirectory) throws TransferFailedException,
        ResourceDoesNotExistException,
            AuthorizationException {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean resourceExists(final String resource)
        throws TransferFailedException {
        try {
            return this.container.getBlockBlobReference(resource).exists();
        } catch (final StorageException | URISyntaxException ex) {
            throw new TransferFailedException(
                String.format("Failed to get resource '%s'", resource), ex
            );
        }
    }

    @Override
    public List<String> getFileList(final String destinationDirectory)
            throws TransferFailedException, ResourceDoesNotExistException,
            AuthorizationException {
        return null;
    }

    @Override
    public boolean supportsDirectoryCopy() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Repository getRepository() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void connect(final Repository source)
            throws ConnectionException, AuthenticationException {
        // TODO Auto-generated method stub

    }

    @Override
    public void connect(final Repository source, final ProxyInfo proxyInfo)
            throws ConnectionException, AuthenticationException {
        // TODO Auto-generated method stub

    }

    @Override
    public void connect(final Repository source,
        final ProxyInfoProvider proxyInfoProvider) throws ConnectionException,
        AuthenticationException {
        // TODO Auto-generated method stub

    }

    @Override
    public void connect(final Repository source,
        final AuthenticationInfo authenticationInfo) throws ConnectionException,
        AuthenticationException {
        // TODO Auto-generated method stub

    }

    @Override
    public void connect(final Repository source,
        final AuthenticationInfo authenticationInfo, final ProxyInfo proxyInfo)
        throws ConnectionException, AuthenticationException {
        // TODO Auto-generated method stub

    }

    @Override
    public void connect(final Repository source,
            final AuthenticationInfo authenticationInfo,
            final ProxyInfoProvider proxyInfoProvider)
            throws ConnectionException, AuthenticationException {
        // TODO Auto-generated method stub

    }

    @Override
    public void openConnection() throws ConnectionException,
        AuthenticationException {
        // TODO Auto-generated method stub

    }

    @Override
    public void disconnect() throws ConnectionException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setTimeout(final int timeoutValue) {
        // TODO Auto-generated method stub

    }

    @Override
    public int getTimeout() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setReadTimeout(final int timeoutValue) {
        // TODO Auto-generated method stub

    }

    @Override
    public int getReadTimeout() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void addSessionListener(final SessionListener listener) {
        // Noop
    }

    @Override
    public void removeSessionListener(final SessionListener listener) {
        // Noop
    }

    @Override
    public boolean hasSessionListener(final SessionListener listener) {
        return false;
    }

    @Override
    public void addTransferListener(final TransferListener listener) {
        // Noop
    }

    @Override
    public void removeTransferListener(final TransferListener listener) {
        // Noop
    }

    @Override
    public boolean hasTransferListener(final TransferListener listener) {
        return false;
    }

    @Override
    public boolean isInteractive() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setInteractive(final boolean interactive) {
        // TODO Auto-generated method stub
    }

}
