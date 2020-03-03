package com.image;
import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class ImageUpader {

    private Cloudinary cloudinary;

 public ImageUpader(){
     cloudinary=new Cloudinary(ObjectUtils.asMap(
             "cloud_name", "dypgumpkq",
             "api_key", "143323712644347",
             "api_secret"  , "JxL_K97sWrJip1gaC2u59mwPGt4"
     ));
 }


public String  uploadFile(String path) throws IOException {
    File file=new File(path);
    Map uploadResult=cloudinary
            .uploader()
            .upload(file, ObjectUtils.emptyMap());
return uploadResult.get("url").toString();
 }


public String uploadFile(File path) throws IOException {
     Map uploadResult=cloudinary
             .uploader()
             .upload(path, ObjectUtils.emptyMap());
     return uploadResult.get("url").toString();
}



}
