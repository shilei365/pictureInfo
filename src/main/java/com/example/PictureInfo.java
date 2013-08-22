package com.example;


import java.io.*;

@Deprecated //remove this class
public class PictureInfo
{
    public final static int UNKNOWN      = -1;
    public final static int DIRECTORY    =  0;
    public final static int GIF_PICTURE  =  1;
    public final static int JPEG_PICTURE =  2;

    public int getFileType (File file)
    {
        byte [] fileStart = new byte [0];
        if (file.isDirectory ())
        {
            return DIRECTORY;
        }
        try {
            FileInputStream     fis = new FileInputStream (file);
            BufferedInputStream bis = new BufferedInputStream (fis);
            DataInputStream     dis = new DataInputStream (bis);
            fileStart = new byte [(int) Math.min (file.length (), 10)];
            dis.read (fileStart);
            dis.close ();
            bis.close ();
            fis.close ();
        } catch (Exception e) {
            e.printStackTrace ();
        }
        if (fileStart.length >= 6)
        {
            if ((fileStart [0] == (byte) 'G') &&
                    (fileStart [1] == (byte) 'I') &&
                    (fileStart [2] == (byte) 'F') &&
                    (fileStart [3] == (byte) '8') &&
                    (fileStart [5] == (byte) 'a'))
            {
                return GIF_PICTURE;
            }
        }
        if (fileStart.length >= 10)
        {
            if ((fileStart [0] == (byte) 0xFF) &&
                    (fileStart [1] == (byte) 0xD8) &&
                    (fileStart [2] == (byte) 0xFF) &&
                    (fileStart [3] == (byte) 0xE0) &&
                    (fileStart [6] == (byte) 'J')  &&
                    (fileStart [7] == (byte) 'F')  &&
                    (fileStart [8] == (byte) 'I')  &&
                    (fileStart [9] == (byte) 'F'))
            {
                return JPEG_PICTURE;
            }
        }
        return UNKNOWN;
    }

    public long getFileSize (File file)
    {
        return file.length ();
    }

    public int [] getGIFSize (File file)
    {
        FileInputStream     fis = null;
        BufferedInputStream bis = null;
        DataInputStream     dis = null;
        try {
            fis = new FileInputStream (file);
            bis = new BufferedInputStream (fis);
            dis = new DataInputStream (bis);

            dis.skip (6);
            int wLow  = dis.readUnsignedByte ();
            int wHigh = dis.readUnsignedByte ();
            int hLow  = dis.readUnsignedByte ();
            int hHigh = dis.readUnsignedByte ();

            int [] result = new int [2];
            result [0] = (wHigh << 8) | (wLow & 0xFF);
            result [1] = (hHigh << 8) | (hLow & 0xFF);
            return result;
        } catch (Exception e) {
            e.printStackTrace ();
        } finally {
            try {
                if (dis != null) dis.close ();
                if (bis != null) bis.close ();
                if (fis != null) fis.close ();
            } catch (Exception e) {
            }
        }
        return null;
    }

    public java.awt.Dimension getJPEGSize (File file)
    {
        FileInputStream     fis = null;
        BufferedInputStream bis = null;
        DataInputStream     dis = null;
        try {
            fis = new FileInputStream (file);
            bis = new BufferedInputStream (fis);
            dis = new DataInputStream (bis);

            if (dis.readUnsignedByte () != 0xFF) return null;
            if (dis.readUnsignedByte () != 0xD8) return null;
            while (true)
            {
                int value = dis.readUnsignedByte ();
                while (value == 0xFF)
                {
                    value = dis.readUnsignedByte ();
                }
                int size = dis.readUnsignedShort ();
                if ((value >= 0xC0) && (value <= 0xC3))
                {
                    dis.readByte ();
                    int s1 = dis.readUnsignedShort ();
                    int s2 = dis.readUnsignedShort ();
                    return new java.awt.Dimension (s2, s1);
                }
                else
                {
                    dis.skip (size - 2);
                }
            }
        } catch (Exception e) {
            e.printStackTrace ();
        } finally {
            try {
                if (dis != null) dis.close ();
                if (bis != null) bis.close ();
                if (fis != null) fis.close ();
            } catch (Exception e) {
            }
        }
        return null;
    }

    public static void main (String [] args)
    {
        PictureInfo info = new PictureInfo ();
        for (int i = 0; i < args.length; i++)
        {
            File file = new File (args [i]);
            int type = info.getFileType (file);
            switch (type)
            {
                case DIRECTORY:
                    System.out.println ("name: "+args [i]+", type: directory");
                    break;
                case GIF_PICTURE:
                    long gifLength = info.getFileSize (file);
                    int [] gifSize = info.getGIFSize (file);
                    if (gifSize != null)
                    {
                        System.out.println ("name: "+args [i]+", type: GIF-image");
                        System.out.println ("  length:      "+gifLength);
                        System.out.println ("  size:        "+gifSize [0]+" x "+gifSize [1]);
                        System.out.println ("  compression: "+(gifLength * 100 / (gifSize [0] * gifSize [1]))+"%");
                    }
                    break;
                case JPEG_PICTURE:
                    long jpegLength = info.getFileSize (file);
                    java.awt.Dimension jpegSize = info.getJPEGSize (file);
                    if (jpegSize != null)
                    {
                        System.out.println ("name: "+args [i]+", type: JPEG-image");
                        System.out.println ("  length:      "+jpegLength);
                        System.out.println ("  size:        "+jpegSize.width+" x "+jpegSize.height);
                        System.out.println ("  compression: "+(jpegLength * 100 / (jpegSize.width * jpegSize.height * 3))+"%");
                    }
                    break;
                default:
                    System.out.println ("name: "+args [i]+", type: unknown");
                    break;
            }
        }
    }
}