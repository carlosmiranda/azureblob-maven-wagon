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

import com.google.common.collect.ImmutableMap;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.maven.wagon.Wagon;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Integration test for {@link AzureBlobWagon}.
 *
 * <p> This integration test can use either a real Azure Storage account or
 * emulated storage.
 *
 * <p>To use Emulated Storage, you must specify your account name and key. This
 * can be done by specifying the environment variables
 * <code>storage.name</code> and <code>storage.key</code> respectively, with the
 * appropriate values.
 *
 * <p>To use Emulated Storage, you must specify the environment variable
 * <code>storage.emulated</code> with a value of <code>true</code>. Note that if
 * specified, use of emulated storage will take precedence over the real Azure
 * storage account details.
 *
 * The IT case will create and delete a container named
 * <code>wagon-test-[suffix]</code>, where [suffix] is a random string with 20
 * lowercase characters.
 *
 * @author Carlos Miranda (miranda.cma+azureblob.wagon@gmail.com)
 */
public final class AzureBlobWagonITCase {
    /**
     * Storage account.
     */
    private static final String STORAGE_ACCOUNT =
        System.getProperty("storage.account");
    /**
     * Storage key.
     */
    private static final String STORAGE_KEY = System.getProperty("storage.key");
    /**
     * Storage container name.
     */
    private static final String STORAGE_CONTAINER = String.format(
        // @checkstyle MagicNumber (2 lines)
        "%s-%s", "wagon-test",
        RandomStringUtils.randomAlphabetic(20).toLowerCase()
    );
    /**
     * Storage container.
     */
    private static final boolean EMULATED_STORAGE =
        Boolean.valueOf(System.getProperty("storage.emulated"));

    /**
     * Files.
     */
    private static final Map<String, String> FILES = ImmutableMap.of(
        "path/to/first.txt", "abcde",
        "path/to/second.txt", "defgh",
        "another/path/here.txt", "12345"
    );

    /**
     * Set up container and files.
     * @throws Exception If something goes wrong
     */
    @BeforeClass
    public static void createContainerAndFiles() throws Exception {
        Assume.assumeTrue(AzureBlobWagonITCase.testShouldExecute());
        final CloudBlobContainer container = AzureBlobWagonITCase.container();
        container.createIfNotExists();
        for (final Entry<String, String> entry
            : AzureBlobWagonITCase.FILES.entrySet()) {
            final byte[] data =
                entry.getValue().getBytes(StandardCharsets.UTF_8);
            container.getBlockBlobReference(entry.getKey())
                .uploadFromByteArray(data, 0, data.length);
        }
    }

    /**
     * AzureBlobWagon can check if resources exist.
     * @throws Exception If something goes wrong
     */
    @Test
    public void checksIfResourcesExist() throws Exception {
        final Wagon wagon =
            new AzureBlobWagon(AzureBlobWagonITCase.container());
        for (final String file : AzureBlobWagonITCase.FILES.keySet()) {
            MatcherAssert.assertThat(
                wagon.resourceExists(file), Matchers.is(true)
            );
        }
        MatcherAssert.assertThat(
            wagon.resourceExists("does/not/exist"), Matchers.is(false)
        );
    }

    /**
     * Remove container afterwards.
     * @throws Exception If something goes wrong.
     */
    @AfterClass
    public static void removeContainer() throws Exception {
        if (AzureBlobWagonITCase.testShouldExecute()) {
            AzureBlobWagonITCase.container().deleteIfExists();
        }
    }

    /**
     * Get Azure Blob Container.
     * @return Azure Blob Container.
     * @throws Exception If something goes wrong
     */
    private static CloudBlobContainer container() throws Exception {
        final CloudStorageAccount account = AzureBlobWagonITCase.account();
        return account.createCloudBlobClient().
            getContainerReference(AzureBlobWagonITCase.STORAGE_CONTAINER);
    }

    /**
     * Get cloud storage account.
     * @return Cloud storage account.
     * @throws Exception if something goes wrong
     */
    private static CloudStorageAccount account() throws Exception {
        final CloudStorageAccount account;
        if (AzureBlobWagonITCase.EMULATED_STORAGE) {
            account = CloudStorageAccount.getDevelopmentStorageAccount();
        } else {
            account = CloudStorageAccount.parse(
                new AzureStorageCredentials(
                    AzureBlobWagonITCase.STORAGE_ACCOUNT,
                    AzureBlobWagonITCase.STORAGE_KEY,
                    false
                ).connectionString()
            );
        }
        return account;
    }

    /**
     * Should this test execute?
     * @return true, if proper environment variables are specified
     */
    private static boolean testShouldExecute() {
        return AzureBlobWagonITCase.EMULATED_STORAGE
            || AzureBlobWagonITCase.STORAGE_ACCOUNT != null
            && AzureBlobWagonITCase.STORAGE_KEY != null;
    }

}
