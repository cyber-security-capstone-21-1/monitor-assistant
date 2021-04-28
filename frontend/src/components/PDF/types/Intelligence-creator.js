import React, { useState } from "react";
import {
  Page,
  Text,
  Image,
  Font,
  View,
  Document,
  StyleSheet,
} from "@react-pdf/renderer";

export default function IntelligenceCreator(props) {
  Font.register({
    family: "nanumGothic",
    src: "https://fonts.gstatic.com/ea/nanumgothic/v5/NanumGothic-Regular.ttf",
  });

  Font.register({
    family: "nanumGothicBold",
    src: "https://fonts.gstatic.com/ea/nanumgothic/v5/NanumGothic-Bold.ttf",
  });

  const styles = StyleSheet.create({
    body: {
      paddingTop: 35,
      paddingBottom: 65,
      paddingHorizontal: 35,
    },
    title: {
      fontSize: 24,
      textAlign: "left",
      color: "#2d2e30",
      fontFamily: "nanumGothicBold",
      marginBottom: 20,
    },
    text: {
      margin: 12,
      fontSize: 14,
      textAlign: "justify",
      fontFamily: "nanumGothic",
    },
    header: {
      fontSize: 10,
      marginBottom: 20,
      textAlign: "right",
      color: "#81848a",
      fontFamily: "nanumGothic",
    },
    pageNumber: {
      position: "absolute",
      fontSize: 12,
      bottom: 30,
      left: 0,
      right: 0,
      textAlign: "center",
      color: "grey",
    },
  });
  return (
    <Document>
      <Page style={styles.body}>
        <Text style={styles.header} fixed>
          사이버수사관 모니터링 보조 업무 시스템
        </Text>
        <Text style={styles.title} fixed>
          게시물 모니터링 위험 징후 보고
        </Text>
        <Text style={styles.text}>첩보명</Text>
        <Text style={styles.text}>수사관</Text>
        <Text style={styles.text}>첩보 유형</Text>
        <Text style={styles.text}>관련 법</Text>
        <Text style={styles.text}>게시물명</Text>
        <Text style={styles.text}>사이트</Text>
        <Text style={styles.text}>작성자</Text>
        <Text style={styles.text}>작성일</Text>
        <Text style={styles.text}>URL</Text>
        <Text style={styles.text}>첩보 내용</Text>
        <Text style={styles.text}>첩보 내용</Text>
        <Text style={styles.text}>수사관 의견</Text>
        <Text style={styles.text}>내사 진행 여부</Text>
        <Text style={styles.text}>페이지 스냅샷</Text>
        <Text
          style={styles.pageNumber}
          render={({ pageNumber, totalPages }) =>
            `${pageNumber} / ${totalPages}`
          }
          fixed
        />
      </Page>
    </Document>
  );
}
