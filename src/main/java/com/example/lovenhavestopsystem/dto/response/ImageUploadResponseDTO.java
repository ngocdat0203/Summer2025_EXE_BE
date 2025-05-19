package com.example.lovenhavestopsystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageUploadResponseDTO {
    private List<ImageResponseDTO> successFiles;
    private List<String> failedFiles;
}