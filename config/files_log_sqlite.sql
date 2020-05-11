/*
 Navicat Premium Data Transfer

 Source Server         : sqlite_pacs
 Source Server Type    : SQLite
 Source Server Version : 3021000
 Source Schema         : main

 Target Server Type    : SQLite
 Target Server Version : 3021000
 File Encoding         : 65001

 Date: 11/05/2020 11:23:43
*/

PRAGMA foreign_keys = false;

-- ----------------------------
-- Table structure for files_log
-- ----------------------------
DROP TABLE IF EXISTS "files_log";
CREATE TABLE "files_log" (
  "id" integer NOT NULL PRIMARY KEY AUTOINCREMENT,
  "patient_name" text(255),
  "age" text(50),
  "uid" text(120),
  "file_count" text(255),
  "file_size" real,
  "upload_status" integer(11),
  "study_no" text(255),
  "start_time" text,
  "end_time" text
);

-- ----------------------------
-- Auto increment value for files_log
-- ----------------------------
UPDATE "sqlite_sequence" SET seq = 2239 WHERE name = 'files_log';

PRAGMA foreign_keys = true;
