package com.trgroup.mr;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.stream.StreamSupport;

public class FlowCount {
    static class FlowCountMapper extends Mapper<LongWritable, Text, Text, FlowBean> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String[] fields = line.split("\t");
            String phoneNo = fields[0];
            long upFlow = Long.parseLong(fields[1]);
            long downFlow = Long.parseLong(fields[2]);
            context.write(new Text(phoneNo), new FlowBean(upFlow, downFlow));
        }
    }

    static class FlowCountReducer extends Reducer<Text, FlowBean, Text, FlowBean> {

        @Override
        protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException {
            long sum_upFlow = 0;
            long sum_downFlow = 0;
            FlowBean flowBean = StreamSupport.stream(values.spliterator(), false).reduce((x, y) -> new FlowBean((x.getUpFlow() + y.getUpFlow()), (x.getDownFlow() + y.getDownFlow()))).orElse(new FlowBean(0, 0));
            context.write(key, flowBean);
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration);
        job.setJarByClass(FlowCount.class);
        job.setMapperClass(FlowCountMapper.class);
        job.setReducerClass(FlowCountReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));


        boolean res = job.waitForCompletion(true);
        System.exit(res ? 0 : 1);
    }
}
