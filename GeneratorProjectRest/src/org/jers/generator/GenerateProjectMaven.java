package org.jers.generator;

import org.jers.generator.enums.Packaging;

import java.io.File;

import static org.jers.generator.GenerateFile.writeFile;
import static org.jers.generator.java.GenerateApplication.generate;
import static org.jers.generator.Config.*;


public class GenerateProjectMaven {

    private static void createWrapperFile() {
        String text = "# Licensed to the Apache Software Foundation (ASF) under one\n" +
                "# or more contributor license agreements.  See the NOTICE file\n" +
                "# distributed with this work for additional information\n" +
                "# regarding copyright ownership.  The ASF licenses this file\n" +
                "# to you under the Apache License, Version 2.0 (the\n" +
                "# \"License\"); you may not use this file except in compliance\n" +
                "# with the License.  You may obtain a copy of the License at\n" +
                "#\n" +
                "#   https://www.apache.org/licenses/LICENSE-2.0\n" +
                "#\n" +
                "# Unless required by applicable law or agreed to in writing,\n" +
                "# software distributed under the License is distributed on an\n" +
                "# \"AS IS\" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY\n" +
                "# KIND, either express or implied.  See the License for the\n" +
                "# specific language governing permissions and limitations\n" +
                "# under the License.\n" +
                "wrapperVersion=3.3.2\n" +
                "distributionType=only-script\n" +
                "distributionUrl=https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/" + MAVEN_VERSION + "/apache-maven-" + MAVEN_VERSION + "-bin.zip";
        writeFile(ROUTE + "\\" + ARTEFACT_ID + "\\.mvn\\wrapper\\maven-wrapper.properties", text);
    }

    private static void generateMvnw() {
        String text = "#!/bin/sh\n" +
                "# ----------------------------------------------------------------------------\n" +
                "# Licensed to the Apache Software Foundation (ASF) under one\n" +
                "# or more contributor license agreements.  See the NOTICE file\n" +
                "# distributed with this work for additional information\n" +
                "# regarding copyright ownership.  The ASF licenses this file\n" +
                "# to you under the Apache License, Version 2.0 (the\n" +
                "# \"License\"); you may not use this file except in compliance\n" +
                "# with the License.  You may obtain a copy of the License at\n" +
                "#\n" +
                "#    http://www.apache.org/licenses/LICENSE-2.0\n" +
                "#\n" +
                "# Unless required by applicable law or agreed to in writing,\n" +
                "# software distributed under the License is distributed on an\n" +
                "# \"AS IS\" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY\n" +
                "# KIND, either express or implied.  See the License for the\n" +
                "# specific language governing permissions and limitations\n" +
                "# under the License.\n" +
                "# ----------------------------------------------------------------------------\n" +
                "\n" +
                "# ----------------------------------------------------------------------------\n" +
                "# Apache Maven Wrapper startup batch script, version 3.3.2\n" +
                "#\n" +
                "# Optional ENV vars\n" +
                "# -----------------\n" +
                "#   JAVA_HOME - location of a JDK home dir, required when download maven via java source\n" +
                "#   MVNW_REPOURL - repo url base for downloading maven distribution\n" +
                "#   MVNW_USERNAME/MVNW_PASSWORD - user and password for downloading maven\n" +
                "#   MVNW_VERBOSE - true: enable verbose log; debug: trace the mvnw script; others: silence the output\n" +
                "# ----------------------------------------------------------------------------\n" +
                "\n" +
                "set -euf\n" +
                "[ \"${MVNW_VERBOSE-}\" != debug ] || set -x\n" +
                "\n" +
                "# OS specific support.\n" +
                "native_path() { printf %s\\\\n \"$1\"; }\n" +
                "case \"$(uname)\" in\n" +
                "CYGWIN* | MINGW*)\n" +
                "  [ -z \"${JAVA_HOME-}\" ] || JAVA_HOME=\"$(cygpath --unix \"$JAVA_HOME\")\"\n" +
                "  native_path() { cygpath --path --windows \"$1\"; }\n" +
                "  ;;\n" +
                "esac\n" +
                "\n" +
                "# set JAVACMD and JAVACCMD\n" +
                "set_java_home() {\n" +
                "  # For Cygwin and MinGW, ensure paths are in Unix format before anything is touched\n" +
                "  if [ -n \"${JAVA_HOME-}\" ]; then\n" +
                "    if [ -x \"$JAVA_HOME/jre/sh/java\" ]; then\n" +
                "      # IBM's JDK on AIX uses strange locations for the executables\n" +
                "      JAVACMD=\"$JAVA_HOME/jre/sh/java\"\n" +
                "      JAVACCMD=\"$JAVA_HOME/jre/sh/javac\"\n" +
                "    else\n" +
                "      JAVACMD=\"$JAVA_HOME/bin/java\"\n" +
                "      JAVACCMD=\"$JAVA_HOME/bin/javac\"\n" +
                "\n" +
                "      if [ ! -x \"$JAVACMD\" ] || [ ! -x \"$JAVACCMD\" ]; then\n" +
                "        echo \"The JAVA_HOME environment variable is not defined correctly, so mvnw cannot run.\" >&2\n" +
                "        echo \"JAVA_HOME is set to \\\"$JAVA_HOME\\\", but \\\"\\$JAVA_HOME/bin/java\\\" or \\\"\\$JAVA_HOME/bin/javac\\\" does not exist.\" >&2\n" +
                "        return 1\n" +
                "      fi\n" +
                "    fi\n" +
                "  else\n" +
                "    JAVACMD=\"$(\n" +
                "      'set' +e\n" +
                "      'unset' -f command 2>/dev/null\n" +
                "      'command' -v java\n" +
                "    )\" || :\n" +
                "    JAVACCMD=\"$(\n" +
                "      'set' +e\n" +
                "      'unset' -f command 2>/dev/null\n" +
                "      'command' -v javac\n" +
                "    )\" || :\n" +
                "\n" +
                "    if [ ! -x \"${JAVACMD-}\" ] || [ ! -x \"${JAVACCMD-}\" ]; then\n" +
                "      echo \"The java/javac command does not exist in PATH nor is JAVA_HOME set, so mvnw cannot run.\" >&2\n" +
                "      return 1\n" +
                "    fi\n" +
                "  fi\n" +
                "}\n" +
                "\n" +
                "# hash string like Java String::hashCode\n" +
                "hash_string() {\n" +
                "  str=\"${1:-}\" h=0\n" +
                "  while [ -n \"$str\" ]; do\n" +
                "    char=\"${str%\"${str#?}\"}\"\n" +
                "    h=$(((h * 31 + $(LC_CTYPE=C printf %d \"'$char\")) % 4294967296))\n" +
                "    str=\"${str#?}\"\n" +
                "  done\n" +
                "  printf %x\\\\n $h\n" +
                "}\n" +
                "\n" +
                "verbose() { :; }\n" +
                "[ \"${MVNW_VERBOSE-}\" != true ] || verbose() { printf %s\\\\n \"${1-}\"; }\n" +
                "\n" +
                "die() {\n" +
                "  printf %s\\\\n \"$1\" >&2\n" +
                "  exit 1\n" +
                "}\n" +
                "\n" +
                "trim() {\n" +
                "  # MWRAPPER-139:\n" +
                "  #   Trims trailing and leading whitespace, carriage returns, tabs, and linefeeds.\n" +
                "  #   Needed for removing poorly interpreted newline sequences when running in more\n" +
                "  #   exotic environments such as mingw bash on Windows.\n" +
                "  printf \"%s\" \"${1}\" | tr -d '[:space:]'\n" +
                "}\n" +
                "\n" +
                "# parse distributionUrl and optional distributionSha256Sum, requires .mvn/wrapper/maven-wrapper.properties\n" +
                "while IFS=\"=\" read -r key value; do\n" +
                "  case \"${key-}\" in\n" +
                "  distributionUrl) distributionUrl=$(trim \"${value-}\") ;;\n" +
                "  distributionSha256Sum) distributionSha256Sum=$(trim \"${value-}\") ;;\n" +
                "  esac\n" +
                "done <\"${0%/*}/.mvn/wrapper/maven-wrapper.properties\"\n" +
                "[ -n \"${distributionUrl-}\" ] || die \"cannot read distributionUrl property in ${0%/*}/.mvn/wrapper/maven-wrapper.properties\"\n" +
                "\n" +
                "case \"${distributionUrl##*/}\" in\n" +
                "maven-mvnd-*bin.*)\n" +
                "  MVN_CMD=mvnd.sh _MVNW_REPO_PATTERN=/maven/mvnd/\n" +
                "  case \"${PROCESSOR_ARCHITECTURE-}${PROCESSOR_ARCHITEW6432-}:$(uname -a)\" in\n" +
                "  *AMD64:CYGWIN* | *AMD64:MINGW*) distributionPlatform=windows-amd64 ;;\n" +
                "  :Darwin*x86_64) distributionPlatform=darwin-amd64 ;;\n" +
                "  :Darwin*arm64) distributionPlatform=darwin-aarch64 ;;\n" +
                "  :Linux*x86_64*) distributionPlatform=linux-amd64 ;;\n" +
                "  *)\n" +
                "    echo \"Cannot detect native platform for mvnd on $(uname)-$(uname -m), use pure java version\" >&2\n" +
                "    distributionPlatform=linux-amd64\n" +
                "    ;;\n" +
                "  esac\n" +
                "  distributionUrl=\"${distributionUrl%-bin.*}-$distributionPlatform.zip\"\n" +
                "  ;;\n" +
                "maven-mvnd-*) MVN_CMD=mvnd.sh _MVNW_REPO_PATTERN=/maven/mvnd/ ;;\n" +
                "*) MVN_CMD=\"mvn${0##*/mvnw}\" _MVNW_REPO_PATTERN=/org/apache/maven/ ;;\n" +
                "esac\n" +
                "\n" +
                "# apply MVNW_REPOURL and calculate MAVEN_HOME\n" +
                "# maven home pattern: ~/.m2/wrapper/dists/{apache-maven-<version>,maven-mvnd-<version>-<platform>}/<hash>\n" +
                "[ -z \"${MVNW_REPOURL-}\" ] || distributionUrl=\"$MVNW_REPOURL$_MVNW_REPO_PATTERN${distributionUrl#*\"$_MVNW_REPO_PATTERN\"}\"\n" +
                "distributionUrlName=\"${distributionUrl##*/}\"\n" +
                "distributionUrlNameMain=\"${distributionUrlName%.*}\"\n" +
                "distributionUrlNameMain=\"${distributionUrlNameMain%-bin}\"\n" +
                "MAVEN_USER_HOME=\"${MAVEN_USER_HOME:-${HOME}/.m2}\"\n" +
                "MAVEN_HOME=\"${MAVEN_USER_HOME}/wrapper/dists/${distributionUrlNameMain-}/$(hash_string \"$distributionUrl\")\"\n" +
                "\n" +
                "exec_maven() {\n" +
                "  unset MVNW_VERBOSE MVNW_USERNAME MVNW_PASSWORD MVNW_REPOURL || :\n" +
                "  exec \"$MAVEN_HOME/bin/$MVN_CMD\" \"$@\" || die \"cannot exec $MAVEN_HOME/bin/$MVN_CMD\"\n" +
                "}\n" +
                "\n" +
                "if [ -d \"$MAVEN_HOME\" ]; then\n" +
                "  verbose \"found existing MAVEN_HOME at $MAVEN_HOME\"\n" +
                "  exec_maven \"$@\"\n" +
                "fi\n" +
                "\n" +
                "case \"${distributionUrl-}\" in\n" +
                "*?-bin.zip | *?maven-mvnd-?*-?*.zip) ;;\n" +
                "*) die \"distributionUrl is not valid, must match *-bin.zip or maven-mvnd-*.zip, but found '${distributionUrl-}'\" ;;\n" +
                "esac\n" +
                "\n" +
                "# prepare tmp dir\n" +
                "if TMP_DOWNLOAD_DIR=\"$(mktemp -d)\" && [ -d \"$TMP_DOWNLOAD_DIR\" ]; then\n" +
                "  clean() { rm -rf -- \"$TMP_DOWNLOAD_DIR\"; }\n" +
                "  trap clean HUP INT TERM EXIT\n" +
                "else\n" +
                "  die \"cannot create temp dir\"\n" +
                "fi\n" +
                "\n" +
                "mkdir -p -- \"${MAVEN_HOME%/*}\"\n" +
                "\n" +
                "# Download and Install Apache Maven\n" +
                "verbose \"Couldn't find MAVEN_HOME, downloading and installing it ...\"\n" +
                "verbose \"Downloading from: $distributionUrl\"\n" +
                "verbose \"Downloading to: $TMP_DOWNLOAD_DIR/$distributionUrlName\"\n" +
                "\n" +
                "# select .zip or .tar.gz\n" +
                "if ! command -v unzip >/dev/null; then\n" +
                "  distributionUrl=\"${distributionUrl%.zip}.tar.gz\"\n" +
                "  distributionUrlName=\"${distributionUrl##*/}\"\n" +
                "fi\n" +
                "\n" +
                "# verbose opt\n" +
                "__MVNW_QUIET_WGET=--quiet __MVNW_QUIET_CURL=--silent __MVNW_QUIET_UNZIP=-q __MVNW_QUIET_TAR=''\n" +
                "[ \"${MVNW_VERBOSE-}\" != true ] || __MVNW_QUIET_WGET='' __MVNW_QUIET_CURL='' __MVNW_QUIET_UNZIP='' __MVNW_QUIET_TAR=v\n" +
                "\n" +
                "# normalize http auth\n" +
                "case \"${MVNW_PASSWORD:+has-password}\" in\n" +
                "'') MVNW_USERNAME='' MVNW_PASSWORD='' ;;\n" +
                "has-password) [ -n \"${MVNW_USERNAME-}\" ] || MVNW_USERNAME='' MVNW_PASSWORD='' ;;\n" +
                "esac\n" +
                "\n" +
                "if [ -z \"${MVNW_USERNAME-}\" ] && command -v wget >/dev/null; then\n" +
                "  verbose \"Found wget ... using wget\"\n" +
                "  wget ${__MVNW_QUIET_WGET:+\"$__MVNW_QUIET_WGET\"} \"$distributionUrl\" -O \"$TMP_DOWNLOAD_DIR/$distributionUrlName\" || die \"wget: Failed to fetch $distributionUrl\"\n" +
                "elif [ -z \"${MVNW_USERNAME-}\" ] && command -v curl >/dev/null; then\n" +
                "  verbose \"Found curl ... using curl\"\n" +
                "  curl ${__MVNW_QUIET_CURL:+\"$__MVNW_QUIET_CURL\"} -f -L -o \"$TMP_DOWNLOAD_DIR/$distributionUrlName\" \"$distributionUrl\" || die \"curl: Failed to fetch $distributionUrl\"\n" +
                "elif set_java_home; then\n" +
                "  verbose \"Falling back to use Java to download\"\n" +
                "  javaSource=\"$TMP_DOWNLOAD_DIR/Downloader.java\"\n" +
                "  targetZip=\"$TMP_DOWNLOAD_DIR/$distributionUrlName\"\n" +
                "  cat >\"$javaSource\" <<-END\n" +
                "\tpublic class Downloader extends java.net.Authenticator\n" +
                "\t{\n" +
                "\t  protected java.net.PasswordAuthentication getPasswordAuthentication()\n" +
                "\t  {\n" +
                "\t    return new java.net.PasswordAuthentication( System.getenv( \"MVNW_USERNAME\" ), System.getenv( \"MVNW_PASSWORD\" ).toCharArray() );\n" +
                "\t  }\n" +
                "\t  public static void main( String[] args ) throws Exception\n" +
                "\t  {\n" +
                "\t    setDefault( new Downloader() );\n" +
                "\t    java.nio.file.Files.copy( java.net.URI.create( args[0] ).toURL().openStream(), java.nio.file.Paths.get( args[1] ).toAbsolutePath().normalize() );\n" +
                "\t  }\n" +
                "\t}\n" +
                "\tEND\n" +
                "  # For Cygwin/MinGW, switch paths to Windows format before running javac and java\n" +
                "  verbose \" - Compiling Downloader.java ...\"\n" +
                "  \"$(native_path \"$JAVACCMD\")\" \"$(native_path \"$javaSource\")\" || die \"Failed to compile Downloader.java\"\n" +
                "  verbose \" - Running Downloader.java ...\"\n" +
                "  \"$(native_path \"$JAVACMD\")\" -cp \"$(native_path \"$TMP_DOWNLOAD_DIR\")\" Downloader \"$distributionUrl\" \"$(native_path \"$targetZip\")\"\n" +
                "fi\n" +
                "\n" +
                "# If specified, validate the SHA-256 sum of the Maven distribution zip file\n" +
                "if [ -n \"${distributionSha256Sum-}\" ]; then\n" +
                "  distributionSha256Result=false\n" +
                "  if [ \"$MVN_CMD\" = mvnd.sh ]; then\n" +
                "    echo \"Checksum validation is not supported for maven-mvnd.\" >&2\n" +
                "    echo \"Please disable validation by removing 'distributionSha256Sum' from your maven-wrapper.properties.\" >&2\n" +
                "    exit 1\n" +
                "  elif command -v sha256sum >/dev/null; then\n" +
                "    if echo \"$distributionSha256Sum  $TMP_DOWNLOAD_DIR/$distributionUrlName\" | sha256sum -c >/dev/null 2>&1; then\n" +
                "      distributionSha256Result=true\n" +
                "    fi\n" +
                "  elif command -v shasum >/dev/null; then\n" +
                "    if echo \"$distributionSha256Sum  $TMP_DOWNLOAD_DIR/$distributionUrlName\" | shasum -a 256 -c >/dev/null 2>&1; then\n" +
                "      distributionSha256Result=true\n" +
                "    fi\n" +
                "  else\n" +
                "    echo \"Checksum validation was requested but neither 'sha256sum' or 'shasum' are available.\" >&2\n" +
                "    echo \"Please install either command, or disable validation by removing 'distributionSha256Sum' from your maven-wrapper.properties.\" >&2\n" +
                "    exit 1\n" +
                "  fi\n" +
                "  if [ $distributionSha256Result = false ]; then\n" +
                "    echo \"Error: Failed to validate Maven distribution SHA-256, your Maven distribution might be compromised.\" >&2\n" +
                "    echo \"If you updated your Maven version, you need to update the specified distributionSha256Sum property.\" >&2\n" +
                "    exit 1\n" +
                "  fi\n" +
                "fi\n" +
                "\n" +
                "# unzip and move\n" +
                "if command -v unzip >/dev/null; then\n" +
                "  unzip ${__MVNW_QUIET_UNZIP:+\"$__MVNW_QUIET_UNZIP\"} \"$TMP_DOWNLOAD_DIR/$distributionUrlName\" -d \"$TMP_DOWNLOAD_DIR\" || die \"failed to unzip\"\n" +
                "else\n" +
                "  tar xzf${__MVNW_QUIET_TAR:+\"$__MVNW_QUIET_TAR\"} \"$TMP_DOWNLOAD_DIR/$distributionUrlName\" -C \"$TMP_DOWNLOAD_DIR\" || die \"failed to untar\"\n" +
                "fi\n" +
                "printf %s\\\\n \"$distributionUrl\" >\"$TMP_DOWNLOAD_DIR/$distributionUrlNameMain/mvnw.url\"\n" +
                "mv -- \"$TMP_DOWNLOAD_DIR/$distributionUrlNameMain\" \"$MAVEN_HOME\" || [ -d \"$MAVEN_HOME\" ] || die \"fail to move MAVEN_HOME\"\n" +
                "\n" +
                "clean || :\n" +
                "exec_maven \"$@\"\n";
        writeFile(ROUTE + "\\" + ARTEFACT_ID + "\\mvnw", text);
    }

    private static void generateMvnwCmd() {
        String text = "<# : batch portion\n" +
                "@REM ----------------------------------------------------------------------------\n" +
                "@REM Licensed to the Apache Software Foundation (ASF) under one\n" +
                "@REM or more contributor license agreements.  See the NOTICE file\n" +
                "@REM distributed with this work for additional information\n" +
                "@REM regarding copyright ownership.  The ASF licenses this file\n" +
                "@REM to you under the Apache License, Version 2.0 (the\n" +
                "@REM \"License\"); you may not use this file except in compliance\n" +
                "@REM with the License.  You may obtain a copy of the License at\n" +
                "@REM\n" +
                "@REM    http://www.apache.org/licenses/LICENSE-2.0\n" +
                "@REM\n" +
                "@REM Unless required by applicable law or agreed to in writing,\n" +
                "@REM software distributed under the License is distributed on an\n" +
                "@REM \"AS IS\" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY\n" +
                "@REM KIND, either express or implied.  See the License for the\n" +
                "@REM specific language governing permissions and limitations\n" +
                "@REM under the License.\n" +
                "@REM ----------------------------------------------------------------------------\n" +
                "\n" +
                "@REM ----------------------------------------------------------------------------\n" +
                "@REM Apache Maven Wrapper startup batch script, version 3.3.2\n" +
                "@REM\n" +
                "@REM Optional ENV vars\n" +
                "@REM   MVNW_REPOURL - repo url base for downloading maven distribution\n" +
                "@REM   MVNW_USERNAME/MVNW_PASSWORD - user and password for downloading maven\n" +
                "@REM   MVNW_VERBOSE - true: enable verbose log; others: silence the output\n" +
                "@REM ----------------------------------------------------------------------------\n" +
                "\n" +
                "@IF \"%__MVNW_ARG0_NAME__%\"==\"\" (SET __MVNW_ARG0_NAME__=%~nx0)\n" +
                "@SET __MVNW_CMD__=\n" +
                "@SET __MVNW_ERROR__=\n" +
                "@SET __MVNW_PSMODULEP_SAVE=%PSModulePath%\n" +
                "@SET PSModulePath=\n" +
                "@FOR /F \"usebackq tokens=1* delims==\" %%A IN (`powershell -noprofile \"& {$scriptDir='%~dp0'; $script='%__MVNW_ARG0_NAME__%'; icm -ScriptBlock ([Scriptblock]::Create((Get-Content -Raw '%~f0'))) -NoNewScope}\"`) DO @(\n" +
                "  IF \"%%A\"==\"MVN_CMD\" (set __MVNW_CMD__=%%B) ELSE IF \"%%B\"==\"\" (echo %%A) ELSE (echo %%A=%%B)\n" +
                ")\n" +
                "@SET PSModulePath=%__MVNW_PSMODULEP_SAVE%\n" +
                "@SET __MVNW_PSMODULEP_SAVE=\n" +
                "@SET __MVNW_ARG0_NAME__=\n" +
                "@SET MVNW_USERNAME=\n" +
                "@SET MVNW_PASSWORD=\n" +
                "@IF NOT \"%__MVNW_CMD__%\"==\"\" (%__MVNW_CMD__% %*)\n" +
                "@echo Cannot start maven from wrapper >&2 && exit /b 1\n" +
                "@GOTO :EOF\n" +
                ": end batch / begin powershell #>\n" +
                "\n" +
                "$ErrorActionPreference = \"Stop\"\n" +
                "if ($env:MVNW_VERBOSE -eq \"true\") {\n" +
                "  $VerbosePreference = \"Continue\"\n" +
                "}\n" +
                "\n" +
                "# calculate distributionUrl, requires .mvn/wrapper/maven-wrapper.properties\n" +
                "$distributionUrl = (Get-Content -Raw \"$scriptDir/.mvn/wrapper/maven-wrapper.properties\" | ConvertFrom-StringData).distributionUrl\n" +
                "if (!$distributionUrl) {\n" +
                "  Write-Error \"cannot read distributionUrl property in $scriptDir/.mvn/wrapper/maven-wrapper.properties\"\n" +
                "}\n" +
                "\n" +
                "switch -wildcard -casesensitive ( $($distributionUrl -replace '^.*/','') ) {\n" +
                "  \"maven-mvnd-*\" {\n" +
                "    $USE_MVND = $true\n" +
                "    $distributionUrl = $distributionUrl -replace '-bin\\.[^.]*$',\"-windows-amd64.zip\"\n" +
                "    $MVN_CMD = \"mvnd.cmd\"\n" +
                "    break\n" +
                "  }\n" +
                "  default {\n" +
                "    $USE_MVND = $false\n" +
                "    $MVN_CMD = $script -replace '^mvnw','mvn'\n" +
                "    break\n" +
                "  }\n" +
                "}\n" +
                "\n" +
                "# apply MVNW_REPOURL and calculate MAVEN_HOME\n" +
                "# maven home pattern: ~/.m2/wrapper/dists/{apache-maven-<version>,maven-mvnd-<version>-<platform>}/<hash>\n" +
                "if ($env:MVNW_REPOURL) {\n" +
                "  $MVNW_REPO_PATTERN = if ($USE_MVND) { \"/org/apache/maven/\" } else { \"/maven/mvnd/\" }\n" +
                "  $distributionUrl = \"$env:MVNW_REPOURL$MVNW_REPO_PATTERN$($distributionUrl -replace '^.*'+$MVNW_REPO_PATTERN,'')\"\n" +
                "}\n" +
                "$distributionUrlName = $distributionUrl -replace '^.*/',''\n" +
                "$distributionUrlNameMain = $distributionUrlName -replace '\\.[^.]*$','' -replace '-bin$',''\n" +
                "$MAVEN_HOME_PARENT = \"$HOME/.m2/wrapper/dists/$distributionUrlNameMain\"\n" +
                "if ($env:MAVEN_USER_HOME) {\n" +
                "  $MAVEN_HOME_PARENT = \"$env:MAVEN_USER_HOME/wrapper/dists/$distributionUrlNameMain\"\n" +
                "}\n" +
                "$MAVEN_HOME_NAME = ([System.Security.Cryptography.MD5]::Create().ComputeHash([byte[]][char[]]$distributionUrl) | ForEach-Object {$_.ToString(\"x2\")}) -join ''\n" +
                "$MAVEN_HOME = \"$MAVEN_HOME_PARENT/$MAVEN_HOME_NAME\"\n" +
                "\n" +
                "if (Test-Path -Path \"$MAVEN_HOME\" -PathType Container) {\n" +
                "  Write-Verbose \"found existing MAVEN_HOME at $MAVEN_HOME\"\n" +
                "  Write-Output \"MVN_CMD=$MAVEN_HOME/bin/$MVN_CMD\"\n" +
                "  exit $?\n" +
                "}\n" +
                "\n" +
                "if (! $distributionUrlNameMain -or ($distributionUrlName -eq $distributionUrlNameMain)) {\n" +
                "  Write-Error \"distributionUrl is not valid, must end with *-bin.zip, but found $distributionUrl\"\n" +
                "}\n" +
                "\n" +
                "# prepare tmp dir\n" +
                "$TMP_DOWNLOAD_DIR_HOLDER = New-TemporaryFile\n" +
                "$TMP_DOWNLOAD_DIR = New-Item -Itemtype Directory -Path \"$TMP_DOWNLOAD_DIR_HOLDER.dir\"\n" +
                "$TMP_DOWNLOAD_DIR_HOLDER.Delete() | Out-Null\n" +
                "trap {\n" +
                "  if ($TMP_DOWNLOAD_DIR.Exists) {\n" +
                "    try { Remove-Item $TMP_DOWNLOAD_DIR -Recurse -Force | Out-Null }\n" +
                "    catch { Write-Warning \"Cannot remove $TMP_DOWNLOAD_DIR\" }\n" +
                "  }\n" +
                "}\n" +
                "\n" +
                "New-Item -Itemtype Directory -Path \"$MAVEN_HOME_PARENT\" -Force | Out-Null\n" +
                "\n" +
                "# Download and Install Apache Maven\n" +
                "Write-Verbose \"Couldn't find MAVEN_HOME, downloading and installing it ...\"\n" +
                "Write-Verbose \"Downloading from: $distributionUrl\"\n" +
                "Write-Verbose \"Downloading to: $TMP_DOWNLOAD_DIR/$distributionUrlName\"\n" +
                "\n" +
                "$webclient = New-Object System.Net.WebClient\n" +
                "if ($env:MVNW_USERNAME -and $env:MVNW_PASSWORD) {\n" +
                "  $webclient.Credentials = New-Object System.Net.NetworkCredential($env:MVNW_USERNAME, $env:MVNW_PASSWORD)\n" +
                "}\n" +
                "[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12\n" +
                "$webclient.DownloadFile($distributionUrl, \"$TMP_DOWNLOAD_DIR/$distributionUrlName\") | Out-Null\n" +
                "\n" +
                "# If specified, validate the SHA-256 sum of the Maven distribution zip file\n" +
                "$distributionSha256Sum = (Get-Content -Raw \"$scriptDir/.mvn/wrapper/maven-wrapper.properties\" | ConvertFrom-StringData).distributionSha256Sum\n" +
                "if ($distributionSha256Sum) {\n" +
                "  if ($USE_MVND) {\n" +
                "    Write-Error \"Checksum validation is not supported for maven-mvnd. `nPlease disable validation by removing 'distributionSha256Sum' from your maven-wrapper.properties.\"\n" +
                "  }\n" +
                "  Import-Module $PSHOME\\Modules\\Microsoft.PowerShell.Utility -Function Get-FileHash\n" +
                "  if ((Get-FileHash \"$TMP_DOWNLOAD_DIR/$distributionUrlName\" -Algorithm SHA256).Hash.ToLower() -ne $distributionSha256Sum) {\n" +
                "    Write-Error \"Error: Failed to validate Maven distribution SHA-256, your Maven distribution might be compromised. If you updated your Maven version, you need to update the specified distributionSha256Sum property.\"\n" +
                "  }\n" +
                "}\n" +
                "\n" +
                "# unzip and move\n" +
                "Expand-Archive \"$TMP_DOWNLOAD_DIR/$distributionUrlName\" -DestinationPath \"$TMP_DOWNLOAD_DIR\" | Out-Null\n" +
                "Rename-Item -Path \"$TMP_DOWNLOAD_DIR/$distributionUrlNameMain\" -NewName $MAVEN_HOME_NAME | Out-Null\n" +
                "try {\n" +
                "  Move-Item -Path \"$TMP_DOWNLOAD_DIR/$MAVEN_HOME_NAME\" -Destination $MAVEN_HOME_PARENT | Out-Null\n" +
                "} catch {\n" +
                "  if (! (Test-Path -Path \"$MAVEN_HOME\" -PathType Container)) {\n" +
                "    Write-Error \"fail to move MAVEN_HOME\"\n" +
                "  }\n" +
                "} finally {\n" +
                "  try { Remove-Item $TMP_DOWNLOAD_DIR -Recurse -Force | Out-Null }\n" +
                "  catch { Write-Warning \"Cannot remove $TMP_DOWNLOAD_DIR\" }\n" +
                "}\n" +
                "\n" +
                "Write-Output \"MVN_CMD=$MAVEN_HOME/bin/$MVN_CMD\"\n";
        writeFile(ROUTE + "\\" + ARTEFACT_ID + "\\mvnw.cmd", text);
    }

    private static void generatePOMFile() {
        String text = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "\t\t xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd\">\n" +
                "\t<modelVersion>4.0.0</modelVersion>\n" +
                "\t<parent>\n" +
                "\t\t<groupId>org.springframework.boot</groupId>\n" +
                "\t\t<artifactId>spring-boot-starter-parent</artifactId>\n" +
                "\t\t<version>" + SPRING_VERSION + "</version>\n" +
                "\t\t<relativePath/> <!-- lookup parent from repository -->\n" +
                "\t</parent>\n" +
                "\t<groupId>" + GROUP_ID + "</groupId>\n" +
                "\t<artifactId>" + ARTEFACT_ID + "</artifactId>\n" +
                "\t<version>1.0</version>\n" +
                "\t<packaging>" + PACKAGING.getType() + "</packaging>\n" +
                "\t<name>" + NAME + "</name>\n" +
                "\t<description>" + DESCRIPTION + "</description>\n" +
                "\t<url/>\n" +
                "\t<licenses>\n" +
                "\t\t<license>\n" +
                "\t\t\t<name>Apache License 2.0</name>\n" +
                "\t\t\t<url>https://www.apache.org/licenses/LICENSE-2.0</url>\n" +
                "\t\t</license>\n" +
                "\t</licenses>\n" +
                "\t<developers>\n" +
                "\t\t<developer>\n" +
                "\t\t\t<id>jers</id>\n" +
                "\t\t\t<name>Julian Enrique Rodriguez Saavedra</name>\n" +
                "\t\t\t<email>julianand2009@hotmail.com</email>\n" +
                "\t\t\t<organization></organization>\n" +
                "\t\t\t<organizationUrl></organizationUrl>\n" +
                "\t\t\t<roles>\n" +
                "\t\t\t\t<role>developer</role>\n" +
                "\t\t\t</roles>\n" +
                "\t\t</developer>\n" +
                "\t</developers>\n" +
                "\t<scm>\n" +
                "\t\t<connection></connection>\n" +
                "\t\t<developerConnection></developerConnection>\n" +
                "\t\t<tag>v1.0</tag>\n" +
                "\t\t<url></url>\n" +
                "\t</scm>\n" +
                "\t<properties>\n" +
                "\t\t<java.version>" + JAVA_VERSION + "</java.version>\n" +
                "\t</properties>\n" +
                "\n" +
                "\t<dependencies>\n";
        if(SWAGGER) {
            text = text + "\t\t<dependency>\n" +
                    "\t\t\t<groupId>org.springdoc</groupId>\n" +
                    "\t\t\t<artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>\n" +
                    "\t\t\t<version>" + SWAGGER_VERSION + "</version>\n" +
                    "\t\t</dependency>\n" +
                    "\n";
        }
        text = text + "\t\t<dependency>\n" +
                "\t\t\t<groupId>org.springframework.boot</groupId>\n" +
                "\t\t\t<artifactId>spring-boot-starter-data-jpa</artifactId>\n" +
                "\t\t</dependency>\n" +
                "\n";
        if (SECURITY) {
            text = text + "\t\t<dependency>\n" +
                    "\t\t\t<groupId>org.springframework.boot</groupId>\n" +
                    "\t\t\t<artifactId>spring-boot-starter-security</artifactId>\n" +
                    "\t\t</dependency>\n" +
                    "\n";
        }
        text = text + "\t\t<dependency>\n" +
                "\t\t\t<groupId>org.springframework.boot</groupId>\n" +
                "\t\t\t<artifactId>spring-boot-starter-validation</artifactId>\n" +
                "\t\t</dependency>\n" +
                "\n" +
                "\t\t<dependency>\n" +
                "\t\t\t<groupId>org.springframework.boot</groupId>\n" +
                "\t\t\t<artifactId>spring-boot-starter-web</artifactId>\n" +
                "\t\t</dependency>\n" +
                "\n" +
                "\t\t<dependency>\n" +
                "\t\t\t<groupId>org.postgresql</groupId>\n" +
                "\t\t\t<artifactId>postgresql</artifactId>\n" +
                "\t\t\t<scope>runtime</scope>\n" +
                "\t\t</dependency>\n" +
                "\n";
        if (LOMBOK) {
            text = text + "\t\t<dependency>\n" +
                    "\t\t\t<groupId>org.projectlombok</groupId>\n" +
                    "\t\t\t<artifactId>lombok</artifactId>\n" +
                    "\t\t\t<optional>true</optional>\n" +
                    "\t\t</dependency>\n" +
                    "\n";
        }
        text = text + "\t\t<dependency>\n" +
                "\t\t\t<groupId>org.springframework.boot</groupId>\n" +
                "\t\t\t<artifactId>spring-boot-starter-test</artifactId>\n" +
                "\t\t\t<scope>test</scope>\n" +
                "\t\t</dependency>\n" +
                "\n";
        if (SECURITY) {
            text = text + "\t\t<dependency>\n" +
                    "\t\t\t<groupId>org.springframework.security</groupId>\n" +
                    "\t\t\t<artifactId>spring-security-test</artifactId>\n" +
                    "\t\t\t<scope>test</scope>\n" +
                    "\t\t</dependency>\n";
        }
        text = text + "\t</dependencies>\n" +
                "\n" +
                "\t<build>\n" +
                "\t\t<plugins>\n" +
                "\t\t\t<plugin>\n" +
                "\t\t\t\t<groupId>org.springframework.boot</groupId>\n" +
                "\t\t\t\t<artifactId>spring-boot-maven-plugin</artifactId>\n";
        if (LOMBOK) {
            text = text + "\t\t\t\t<configuration>\n" +
                    "\t\t\t\t\t<excludes>\n" +
                    "\t\t\t\t\t\t<exclude>\n" +
                    "\t\t\t\t\t\t\t<groupId>org.projectlombok</groupId>\n" +
                    "\t\t\t\t\t\t\t<artifactId>lombok</artifactId>\n" +
                    "\t\t\t\t\t\t</exclude>\n" +
                    "\t\t\t\t\t</excludes>\n" +
                    "\t\t\t\t</configuration>\n";
        }
        text = text + "\t\t\t</plugin>\n" +
                "\t\t</plugins>\n" +
                "\t\t<finalName>${artifactId}</finalName>\n" +
                "\t</build>\n" +
                "</project>";
        writeFile(ROUTE + "\\" + ARTEFACT_ID + "\\pom.xml", text);
    }

    public static void generateMavenProject() {
        File folder = new File(ROUTE + "\\" + ARTEFACT_ID);
        if (GenerateFile.deleteExist(folder)) {
            createWrapperFile();
            generateMvnw();
            generateMvnwCmd();
            generatePOMFile();
            generate();
        } else {
            System.out.println("lLa carpeta " + ROUTE + "\\" + ARTEFACT_ID + " no se pudo eliminar");
        }
    }
}