package com.powerchat.gpt.core;

import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.PublicAccessType;
import com.azure.storage.blob.specialized.BlockBlobClient;
import com.azure.storage.common.StorageSharedKeyCredential;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

public class AzureUploader {


    public String storeImage(byte[] image) throws IOException {
        String accountName = "powerchatimg";
        String accountKey = "+8V+SYWbxWmjVruKh1y390BmUoJnAEv4RT8RnnUd+pTlvFFQJUn0oskzWQf5sAtF4MnyP9iiPP/G+AStYgx4KQ==";
        String token = "?sv=2022-11-02&ss=bfqt&srt=c&sp=rwdlacupiytfx&se=2023-06-22T03:25:21Z&st=2023-06-21T19:25:21Z&spr=https&sig=7vIx6kFT1cY5264G9BGLY2Hqjat%2BIdm%2BocyZGB%2FPIws%3D";
        StorageSharedKeyCredential credential = new StorageSharedKeyCredential(accountName, accountKey);
        String endpoint = String.format(Locale.ROOT, "https://%s.blob.core.windows.net", accountName);
        BlobServiceClient storageClient = new BlobServiceClientBuilder().endpoint(endpoint).credential(credential).buildClient();
        BlobContainerClient blobContainerClient = storageClient.getBlobContainerClient("powerchatblob");
        blobContainerClient.createIfNotExists();
        BlockBlobClient blobClient = blobContainerClient.getBlobClient("image"+ System.currentTimeMillis()+".jpg" ).getBlockBlobClient();
        blobClient.upload(new ByteArrayInputStream(image),image.length);
        String URL = blobClient.getBlobUrl();
        return URL;
    }
}
