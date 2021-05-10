import React, { useEffect } from "react";
import {
  Page,
  Text,
  Image,
  Font,
  View,
  Document,
  StyleSheet,
} from "@react-pdf/renderer";

import Logo from "@/assets/images/logo.png";
import { ExtraLight, Light, RText, Medium, SemiBold, Bold, Regular } from "@/assets/fonts/IBMPlexSansKR";

export default function IntelligenceDocument({ content }) {

  console.log(content);

  Font.register({
    family: "IBMPlexSansKR",
    fonts: [
      { src: ExtraLight, fontWeight: 100 },
      { src: Light, fontWeight: 200 },
      { src: RText, fontWeight: 300 },
      { src: Regular, fontWeight: 400 },
      { src: Medium, fontWeight: 500 },
      { src: SemiBold, fontWeight: 600 },
      { src: Bold, fontWeight: 700 },
    ]
  });

  const styles = StyleSheet.create({
    pageDefault: {
      fontFamily: "IBMPlexSansKR",
      fontWeight: 200,
      fontSize: 12,
      paddingTop: 35,
      paddingBottom: 65,
      paddingHorizontal: 35,
    },
    logo: {
      width: 30,
      height: 30,
      marginRight: 5
    },
    header: {
      display: "flex",
      flexDirection: "row",
      justifyContent: "flex-end",
      alignContent: "center",
      fontSize: 10,
      fontWeight: 600,
      marginBottom: 20,
      color: "#81848a",
      fontFamily: "IBMPlexSansKR",
    },
    title: {
      fontSize: 30,
      fontWeight: 700,
      textAlign: "left",
      marginBottom: 40,
      marginLeft: 20
    },
    table: {
      maxWidth: "100%",
      overflowWrap: "break-word"
    },
    tableRow: {
      display: "flex",
      flexDirection: "row",
      justifyContent: "flex-start",
      alignItems: "center",
      marginBottom: 10
    },
    tableCellHead: {
      width: 100,
      fontWeight: 400,
      verticalAlign: "middle"
    },
    tableCellBody: {
      width: "calc(100% - 100)",
      fontSize: 10,
      verticalAlign: "middle"
    }
  });
  return (
    // PDF Document Starts
    <Document>
      {/* A4 Page */}
      <Page wrap={false} style={styles.pageDefault}>
        {/* Header */}
        <View style={styles.header}>
          <Image style={styles.logo} src={Logo}/>
          <View>
            <Text>사이버수사관모니터링</Text>
            <Text>업무보조시스템</Text>
          </View>
        </View>
        {/* Title */}
        <View>
          <Text style={styles.title}>
            게시물 모니터링 위험 징후 보고
          </Text>
        </View>
        {/* Content */}
        {/* Table */}
        <View style={styles.table}>
          {/* Row */}
          <View style={styles.tableRow}>
            {/* Header */}
            <Text style={styles.tableCellHead}>첩보명</Text>
            {/* Body */}
            <Text style={styles.tableCellBody}>학폭 사건의 피해 호소 주장 글로 의심되는 게시물 게재 관련</Text>
          </View>
          {/* Row */}
          <View style={styles.tableRow}>
            {/* Header */}
            <Text style={styles.tableCellHead}>수사관</Text>
            {/* Body */}
            <Text style={styles.tableCellBody}>전지원 (대원)</Text>
          </View>
          {/* Row */}
          <View style={styles.tableRow}>
            {/* Header */}
            <Text style={styles.tableCellHead}>첩보 유형</Text>
            {/* Body */}
            <Text style={styles.tableCellBody}>피해 호소 주장</Text>
          </View>
          {/* Row */}
          <View style={styles.tableRow}>
            {/* Header */}
            <Text style={styles.tableCellHead}>관련 법률</Text>
            {/* Body */}
            <Text style={styles.tableCellBody}>정보통신망법 > ...</Text>
          </View>
          {/* Row */}
          <View style={styles.tableRow}>
            {/* Header */}
            <Text style={styles.tableCellHead}>게시물명</Text>
            {/* Body */}
            <Text style={styles.tableCellBody}>{content.title}</Text>
          </View>
          {/* Row */}
          <View style={styles.tableRow}>
            {/* Header */}
            <Text style={styles.tableCellHead}>사이트</Text>
            {/* Body */}
            <Text style={styles.tableCellBody}>{content.site}</Text>
          </View>
          {/* Row */}
          <View style={styles.tableRow}>
            {/* Header */}
            <Text style={styles.tableCellHead}>작성자</Text>
            {/* Body */}
            <Text style={styles.tableCellBody}>{content.author}</Text>
          </View>
          {/* Row */}
          <View style={styles.tableRow}>
            {/* Header */}
            <Text style={styles.tableCellHead}>작성일</Text>
            {/* Body */}
            <Text style={styles.tableCellBody}>{content.created_at}</Text>
          </View>
          {/* Row */}
          <View style={styles.tableRow}>
            {/* Header */}
            <Text style={styles.tableCellHead}>URL</Text>
            {/* Body */}
            <Text style={styles.tableCellBody}>{content.url}</Text>
          </View>
          {/* Row */}
          <View style={styles.tableRow}>
            {/* Header */}
            <Text style={styles.tableCellHead}>첩보 내용</Text>
            {/* Body */}
            <Text style={styles.tableCellBody}>{content.content}</Text>
          </View>
          {/* Screenshot */}
          <View style={styles.tableRow}>
            <Text style={styles.tableCellHead}>스크린샷</Text>
            <Image style={styles.tableCellBody} src={`/${content.uid}/screenshot.png`} />
          </View>
          {/* End of Table */}
        </View>
        {/* End of Content */}
      </Page>
    </Document>
  );
}
