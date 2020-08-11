package com.example.softwarerepair.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import com.example.softwarerepair.models.FileModel;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StorageUtility {

    public StorageUtility() {
    }

    int result = 0;

    public long deleteAllEmptyFolder(String dir) {

        File f = new File(dir);
        String[] listFiles = f.list();
        long totalSize = 0;
        if (listFiles != null) {
            for (String file : listFiles) {

                File folder = new File(dir + "/" + file);
                if (folder.isDirectory()) {
                    totalSize += deleteAllEmptyFolder(folder.getAbsolutePath());
                } else {
                    totalSize += folder.length();
                }
            }
        }
        if (totalSize == 0) {
            f.delete();
        }
        return totalSize;
    }

    public boolean isSDCard() {

        boolean isSdCard = false;

        boolean isSDPresent = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);

        if (isSDPresent) {
            // yes SD-card is present
            isSdCard = true;
        }
        return isSdCard;
    }


    public void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            if (children != null) {
                for (String child : children) {
                    boolean success = deleteDir(new File(dir, child));
                    if (!success) {
                        return false;
                    }
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    public float getTotalStorage() {
        long totalStorage;

        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        totalStorage = (statFs.getBlockSizeLong() * statFs.getBlockCountLong());

        return totalStorage;
    }


    public static long getAvailableStorage() {
        long megAvailable;

        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        long bytesAvailable;

        bytesAvailable = stat.getAvailableBlocksLong() * stat.getBlockSizeLong();
        megAvailable = bytesAvailable;
        return megAvailable;
    }


    public int getAllEmptyFolders(File current) {

        if (current.isDirectory()) {

            File[] files = current.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.isDirectory()) {
                        if (f.length() == 0) {
                            result++;
                        } else {
                            getAllEmptyFolders(f.getAbsoluteFile());
                        }
                    }
                }
            }
        }
        return result;
    }

    public void copyFileOrDirectory(String srcDir) {

        try {
            File src = new File(srcDir);
            File dst = new File(Environment.getExternalStorageDirectory() + "/SoftwareRepair/Backup_App", +System.currentTimeMillis() + src.getName());

            if (src.isDirectory()) {

                String[] files = src.list();
                int filesLength = 0;
                if (files != null) {
                    filesLength = files.length;
                }
                for (int i = 0; i < filesLength; i++) {
                    String src1 = (new File(src, files[i]).getPath());
                    copyFileOrDirectory(src1);
                }
            } else {
                copyFile(src, dst);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void copyFile(File sourceFile, File destFile) throws IOException {
        if (!Objects.requireNonNull(destFile.getParentFile()).exists())
            destFile.getParentFile().mkdirs();

        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        try (FileChannel source = new FileInputStream(sourceFile).getChannel(); FileChannel destination = new FileOutputStream(destFile).getChannel()) {
            destination.transferFrom(source, 0, source.size());
        }
    }

    public List<FileModel> getAllUnUsableFile(String path) {
        File fold = new File(path);
        List<FileModel> fileList = new ArrayList<>();
        File[] mlist = fold.listFiles();
        File[] mFilelist = fold.listFiles(new AllUnUsableFilter());
        if (mlist != null) {
            for (File f : mlist) {
                if (f.isDirectory()) {
                    List<FileModel> fList = getAllUnUsableFile(f.getAbsolutePath());
                    fileList.addAll(fList);
                }
            }
        }
        if (mFilelist != null) {
            for (File f : mFilelist) {
                FileModel file = new FileModel();
                file.setFileName(f.getName());
                file.setFileSize(f.length());
                //                doc.setType(FileTypes.DocumentType);
                file.setFilePath(f.getAbsolutePath());
                if (f.length() > 0)
                    fileList.add(file);
            }
        }
        return fileList;
    }


    public long getLogFileSize(String path) {
        long size = 0;
        File fold = new File(path);
        File[] mlist = fold.listFiles();
        File[] mFilelist = fold.listFiles(new AllLogFilter());
        if (mlist != null) {
            for (File f : mlist) {
                if (f.isDirectory()) {
                    getLogFileSize(f.getAbsolutePath());
                }
            }
        }
        if (mFilelist != null) {
            for (File f : mFilelist) {
                size = size + f.length();
            }
        }
        return size;
    }

    public long getTempFileSize(String path) {
        long size = 0;
        File fold = new File(path);
        File[] mlist = fold.listFiles();
        File[] mFilelist = fold.listFiles(new AllTempFilter());
        if (mlist != null) {
            for (File f : mlist) {
                if (f.isDirectory()) {
                    getTempFileSize(f.getAbsolutePath());
                }
            }
        }
        if (mFilelist != null) {
            for (File f : mFilelist) {
                size = size + f.length();
            }
        }
        return size;
    } public long getApkFileSize(String path) {
        long size = 0;
        File fold = new File(path);
        File[] mlist = fold.listFiles();
        File[] mFilelist = fold.listFiles(new AllApkFilter());
        if (mlist != null) {
            for (File f : mlist) {
                if (f.isDirectory()) {
                    getApkFileSize(f.getAbsolutePath());
                }
            }
        }
        if (mFilelist != null) {
            for (File f : mFilelist) {
                size = size + f.length();
            }
        }
        return size;
    }

    public long getCachesSize(String path) {
        long size = 0;
        File fold = new File(path);
        File[] mlist = fold.listFiles();
        File[] mFilelist = fold.listFiles(new AllCacheFilter());
        if (mlist != null) {
            for (File f : mlist) {
                if (f.isDirectory()) {
                    getCachesSize(f.getAbsolutePath());
                }
            }
        }
        if (mFilelist != null) {
            for (File f : mFilelist) {
                size = size + f.length();
            }
        }
        return size;
    }
    public long getAllUnUsableSize(String path) {
        long size = 0;
        File fold = new File(path);
        File[] mlist = fold.listFiles();
        File[] mFilelist = fold.listFiles(new AllUnUsableFilter());
        if (mlist != null) {
            for (File f : mlist) {
                if (f.isDirectory()) {
                    getAllUnUsableSize(f.getAbsolutePath());
                }
            }
        }
        if (mFilelist != null) {
            for (File f : mFilelist) {
                size = size + f.length();
            }
        }
        return size;
    }



    public static class AllUnUsableFilter implements FileFilter {
        @Override
        public boolean accept(File pathname) {
            String path = pathname.getPath();
            return (path.endsWith(".tmp")
                    || path.endsWith(".TEMP")
                    || path.endsWith(".csi")
                    || path.endsWith(".cfa")
                    || path.endsWith(".egt")
                    || path.endsWith(".clean")
                    || path.endsWith(".cache")
                    || path.endsWith(".imagecache")
                    || path.endsWith(".dmp")
                    || path.endsWith(".log")
                    || path.endsWith(".apk")
            );
        }
    }

    public static class AllTempFilter implements FileFilter {
        @Override
        public boolean accept(File pathname) {
            String path = pathname.getPath();
            return (path.endsWith(".tmp") || path.endsWith(".TEMP"));
        }
    }
    public static class AllApkFilter implements FileFilter {
        @Override
        public boolean accept(File pathname) {
            String path = pathname.getPath();
            return (path.endsWith(".apk"));
        }
    }

    public static class AllLogFilter implements FileFilter {
        @Override
        public boolean accept(File pathname) {
            String path = pathname.getPath();
            return (path.endsWith(".log"));
        }
    }

    public static class AllCacheFilter implements FileFilter {
        @Override
        public boolean accept(File pathname) {
            String path = pathname.getPath();
            return path.endsWith(".cache");
        }
    }

}
